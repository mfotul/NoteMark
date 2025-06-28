package com.example.notemark.core.data.mapper

import com.example.notemark.core.data.networking.dto.NoteDto
import com.example.notemark.note.domain.Note
import java.time.Instant

fun NoteDto.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        createdAt = Instant.parse(createdAt),
        lastEditedAt = Instant.parse(lastEditedAt)
    )
}

fun Note.toNoteDto(): NoteDto {
    return NoteDto(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt.toString(),
        lastEditedAt = lastEditedAt.toString()
    )
}