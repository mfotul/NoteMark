package com.example.notemark.note.presentation.note

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

class NoteViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val noteMarkRepository: NoteMarkRepository
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val noteId = savedStateHandle.get<String>("id")
    private val fabClick = savedStateHandle.get<Boolean>("fabClick") ?: false

    private var eventChannel = Channel<NoteEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(NoteState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData && noteId != null)
                loadNote(noteId)
            hasLoadedInitialData = true
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NoteState()
        )


    fun onAction(action: NoteAction) {
        when (action) {
            is NoteAction.OnChangeContent -> onChangeContent(action.content)
            is NoteAction.OnChangeTitle -> onChangeTitle(action.title)
            NoteAction.OnCloseClicked -> onClose()
            NoteAction.OnBackClicked -> onBack()
            NoteAction.OnSaveClicked -> onSave()
            NoteAction.OnConfirmDiscardClicked -> confirmDiscard()
            NoteAction.OnConfirmCancelClicked -> confirmCancel()
            NoteAction.OnBookClick -> readerMode()
            NoteAction.OnPencilClick -> editMode()
            NoteAction.OnScreenClick -> showUIElements()
            NoteAction.HideUIElements -> hideUIElements()
        }
    }

    private fun hideUIElements() {
        _state.update {
            it.copy(
                showUIElements = false
            )
        }
    }

    private fun showUIElements() {
        _state.update {
            it.copy(
                showUIElements = true
            )
        }
    }

    private fun readerMode() {
        _state.update {
            if (state.value.mode == NoteMode.VIEW)
                it.copy(
                    mode = NoteMode.READER,
                    readerMode = true,
                    showUIElements = true
                )
            else
                it.copy(
                    mode = NoteMode.VIEW,
                    readerMode = false,
                    showUIElements = true
                )
        }
    }

    private fun editMode() {
        val note = state.value.note
        _state.update {
            it.copy(
                mode = NoteMode.EDIT,
                readerMode = false,
                showUIElements = true,
                title = TextFieldValue(note?.title ?: ""),
                content = TextFieldValue(note?.content ?: "")
            )
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
                showDialog = false,
                mode = NoteMode.VIEW
            )
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
                _state.update {
                    it.copy(
                        mode = NoteMode.VIEW
                    )
                }
            }
        }
    }

    private fun onClose() {
        val note = state.value.note
        if (note != null
            && state.value.title.text == note.title
            && state.value.content.text == note.content
        )
            _state.update {
                it.copy(
                    mode = NoteMode.VIEW
                )
            }
        else
            _state.update {
                it.copy(
                    showDialog = true
                )
            }
    }

    private fun onBack() {
        viewModelScope.launch {
            eventChannel.send(NoteEvent.OnBack)
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
                    content = TextFieldValue(note.content),
                    mode = if (fabClick) NoteMode.EDIT else NoteMode.VIEW
                )
            }
        }.launchIn(viewModelScope)
    }
}