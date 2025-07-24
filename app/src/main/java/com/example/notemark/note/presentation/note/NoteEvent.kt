package com.example.notemark.note.presentation.note

sealed interface NoteEvent {
    object OnClose: NoteEvent
    object OnBack: NoteEvent
}