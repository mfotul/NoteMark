package com.example.notemark.note.presentation.edit

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notemark.core.domain.util.onError
import com.example.notemark.core.presentation.util.SnackBarController
import com.example.notemark.core.presentation.util.SnackBarEvent
import com.example.notemark.note.domain.Note
import com.example.notemark.note.domain.NoteMarkRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant

class EditViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val noteMarkRepository: NoteMarkRepository
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val noteId = savedStateHandle.get<String>("id")

    private var eventChannel = Channel<EditEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(EditState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData && noteId != null)
                loadNote(noteId)
            hasLoadedInitialData = true
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = EditState()
        )


    fun onAction(action: EditAction) {
        when (action) {
            is EditAction.OnChangeContent -> onChangeContent(action.content)
            is EditAction.OnChangeTitle -> onChangeTitle(action.title)
            EditAction.OnCloseClicked -> onClose()
            EditAction.OnSaveClicked -> onSave()
            EditAction.OnConfirmDiscardClicked -> confirmDiscard()
            EditAction.OnConfirmCancelClicked -> confirmCancel()
        }
    }

    private fun confirmCancel() {
        _state.update {
            it.copy(
                showDialog = false
            )
        }
    }

    private fun confirmDiscard() {
        _state.update {
            it.copy(
                showDialog = false
            )
        }
        viewModelScope.launch {
            delay(100L)
            eventChannel.send(EditEvent.OnClose)
        }
    }

    private fun onSave() {
        val note = state.value.note
        if (note != null) {
            viewModelScope.launch {
                val updatedNote = Note(
                    id = note.id,
                    title = state.value.title.text,
                    content = state.value.content.text,
                    createdAt = note.createdAt,
                    lastEditedAt = Instant.now()
                )
                noteMarkRepository.updateNote(updatedNote).onError { error ->
                    SnackBarController.sendEvent(
                        SnackBarEvent(
                            message = error
                        )
                    )
                    return@launch
                }
                eventChannel.send(EditEvent.OnSave)
            }
        }
    }

    private fun onClose() {
        val note = state.value.note
        if (note != null
            && state.value.title.text == note.title
            && state.value.content.text == note.content
        )
            viewModelScope.launch {
                eventChannel.send(EditEvent.OnClose)
            }
        else
            _state.update {
                it.copy(
                    showDialog = true
                )
            }
    }

    private fun onChangeTitle(title: TextFieldValue) {
        _state.update {
            it.copy(
                title = title,
            )
        }
    }

    private fun onChangeContent(content: TextFieldValue) {
        _state.update {
            it.copy(
                content = content
            )
        }
    }

    private fun loadNote(id: String) {
        noteMarkRepository.getNoteById(id).onEach { note ->
            _state.update {
                it.copy(
                    note = note,
                    title = TextFieldValue(note.title),
                    content = TextFieldValue(note.content)
                )
            }
        }.launchIn(viewModelScope)
    }
}