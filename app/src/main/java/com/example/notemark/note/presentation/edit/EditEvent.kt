package com.example.notemark.note.presentation.edit

sealed interface EditEvent {
    object OnClose: EditEvent
    object OnSave: EditEvent
}