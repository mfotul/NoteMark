package com.example.notemark.core.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class NotesResponse(
    val notes: List<NoteDto>,
    val total: Int,
)
