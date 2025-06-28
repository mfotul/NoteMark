package com.example.notemark.core.database.notemark

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteMarkEntity(
    @PrimaryKey val id: String,
    val title: String,
    val content: String,
    val createdAt: Long,
    val lastEditedAt: Long
)
