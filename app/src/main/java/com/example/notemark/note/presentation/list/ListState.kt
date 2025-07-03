package com.example.notemark.note.presentation.list

import androidx.compose.runtime.Immutable
import com.example.notemark.note.domain.Note

@Immutable
data class ListState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = true,
    val showConfirmationDialog: Boolean = false,
    val selectedNote: Note? = null,
    val userInitials: String = "",
)
