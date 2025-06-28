package com.example.notemark.note.presentation.edit

import androidx.compose.ui.text.input.TextFieldValue
import com.example.notemark.note.domain.Note

data class EditState(
    val note: Note? = null,
    val title: TextFieldValue = TextFieldValue(""),
    val content: TextFieldValue = TextFieldValue(""),
    val isLoading: Boolean = false,
    val showDialog: Boolean = false
)
