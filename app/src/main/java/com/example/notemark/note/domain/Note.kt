package com.example.notemark.note.domain

import java.time.Instant

data class Note(
    val id: String,
    val title: String,
    val content: String,
    val createdAt: Instant,
    val lastEditedAt: Instant,
)
