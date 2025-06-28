package com.example.notemark.note.presentation.list

import com.example.notemark.note.domain.Note

sealed interface ListEvent {
    data class NoteSuccessfullyPosted(val note: Note): ListEvent
}