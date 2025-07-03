package com.example.notemark.note.data.database

import com.example.notemark.core.data.database.notemark.NoteMarkEntity
import com.example.notemark.note.domain.Note
import java.time.Instant

fun NoteMarkEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        createdAt = Instant.ofEpochMilli(createdAt),
        lastEditedAt = Instant.ofEpochMilli(lastEditedAt)
    )
}

fun Note.toNoteMarkEntity(): NoteMarkEntity {
    return NoteMarkEntity(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt.toEpochMilli(),
        lastEditedAt = lastEditedAt.toEpochMilli()
    )

}