package com.example.notemark.note.presentation.list

import com.example.notemark.note.domain.Note

sealed interface ListAction {
    data object OnReload: ListAction
    data object OnFabClick: ListAction
    data object OnDialogCancel: ListAction
    data object OnDeleteClick: ListAction
    data class OnNoteLongClick(val note: Note): ListAction
    data object OnSettingsClick: ListAction
}