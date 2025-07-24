package com.example.notemark.note.presentation.note

import androidx.compose.ui.text.input.TextFieldValue

sealed interface NoteAction {
    data class OnChangeTitle(val title: TextFieldValue): NoteAction
    data class OnChangeContent(val content: TextFieldValue): NoteAction
    data object OnSaveClicked : NoteAction
    data object OnCloseClicked : NoteAction
    data object OnBackClicked : NoteAction
    data object OnConfirmDiscardClicked : NoteAction
    data object OnConfirmCancelClicked : NoteAction
    data object OnPencilClick: NoteAction
    data object OnBookClick: NoteAction
    data object OnScreenClick: NoteAction
    data object HideUIElements: NoteAction
}