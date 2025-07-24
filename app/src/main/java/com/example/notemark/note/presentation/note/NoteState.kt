package com.example.notemark.note.presentation.note

import androidx.compose.ui.text.input.TextFieldValue
import com.example.notemark.note.domain.Note

data class NoteState(
    val note: Note? = null,
    val title: TextFieldValue = TextFieldValue(""),
    val content: TextFieldValue = TextFieldValue(""),
    val isLoading: Boolean = false,
    val showDialog: Boolean = false,
    val readerMode: Boolean = false,
    val showUIElements: Boolean = true,
    val mode: NoteMode = NoteMode.VIEW
)
