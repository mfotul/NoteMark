package com.example.notemark.note.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notemark.core.domain.util.onError
import com.example.notemark.core.presentation.util.SnackBarController
import com.example.notemark.core.presentation.util.SnackBarEvent
import com.example.notemark.note.domain.Note
import com.example.notemark.note.domain.NoteMarkDataStore
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
import java.util.UUID

class ListViewModel(
    private val noteMarkRepository: NoteMarkRepository,
    private val noteMarkDataStore: NoteMarkDataStore,
) : ViewModel() {
    var hasLoadedInitialData = false

    private var eventsChannel = Channel<ListEvent>()
    val events = eventsChannel.receiveAsFlow()

    private var _state = MutableStateFlow(ListState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData)
                loadNotes()
            hasLoadedInitialData = true
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ListState()
        )

    fun onAction(action: ListAction) {
        when (action) {
            ListAction.OnReload -> loadNotes()
            ListAction.OnFabClick -> addNote()
            is ListAction.OnNoteLongClick -> showDialog(action.note)
            ListAction.OnDeleteClick -> deleteNote()
            ListAction.OnDialogCancel -> hideDialog()

        }
    }

    private fun showDialog(note: Note) {
        _state.update {
            it.copy(
                showConfirmationDialog = true,
                selectedNote = note
            )
        }
    }

    private fun hideDialog() {
        _state.update {
            it.copy(
                showConfirmationDialog = false,
                selectedNote = null
            )
        }
    }

    private fun deleteNote() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            state.value.selectedNote?.let { noteId ->
                noteMarkRepository.deleteNote(noteId)
                    .onError { error ->
                        SnackBarController.sendEvent(
                            SnackBarEvent(
                                message = error
                            )
                        )
                    }
            }
        }
        _state.update {
            it.copy(
                isLoading = false,
                showConfirmationDialog = false,
                selectedNote = null
            )
        }
    }

    private fun addNote() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        val newNote = Note(
            id = UUID.randomUUID().toString(),
            title = "New Note",
            content = "",
            createdAt = Instant.now(),
            lastEditedAt = Instant.now()
        )
        viewModelScope.launch {
            eventsChannel.send(ListEvent.NoteSuccessfullyPosted(newNote))
            noteMarkRepository.insertNote(newNote).onError { error ->
                SnackBarController.sendEvent(
                    SnackBarEvent(
                        message = error
                    )
                )
            }
        }
        _state.update {
            it.copy(
                isLoading = false
            )
        }
    }

    private fun loadNotes() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        noteMarkRepository.getAllNotes().onEach { notes ->
            _state.update {
                it.copy(
                    notes = notes,
                    isLoading = false,
                    userInitials = nameInitials(
                        fullName = noteMarkDataStore.get()?.userName ?: ""
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun nameInitials(fullName: String): String {
        val parts = fullName.trim().split(Regex("\\s+"))

        return when (parts.size) {
            0 -> ""
            1 -> parts[0].take(2).uppercase()
            else -> "${parts.first().first()}${parts.last().first()}".uppercase()
        }
    }
}
