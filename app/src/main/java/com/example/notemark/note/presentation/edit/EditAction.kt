package com.example.notemark.note.presentation.edit

import androidx.compose.ui.text.input.TextFieldValue

sealed interface EditAction {
    data class OnChangeTitle(val title: TextFieldValue): EditAction
    data class OnChangeContent(val content: TextFieldValue): EditAction
    data object OnSaveClicked : EditAction
    data object OnCloseClicked : EditAction
    data object OnConfirmDiscardClicked : EditAction
    data object OnConfirmCancelClicked : EditAction

}