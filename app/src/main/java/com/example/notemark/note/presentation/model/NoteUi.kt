package com.example.notemark.note.presentation.model

import com.example.notemark.note.domain.Note
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

data class NoteUi(
    val id: String,
    val title: String,
    val content: String,
    val createdAt: Instant,
    val lastEditedAt: Instant,
) {
    val createdAtDate = createdAt.toNoteUiDate()
    val createdAtDateTime = createdAt.toNoteUiDateTime()
    val lastEditedAtDateTime = lastEditedAt.toNoteUiDateTime()
}

fun Note.toNoteUi() = NoteUi(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt,
    lastEditedAt = lastEditedAt
)

fun Instant.toNoteUiDate(): String {
    val zoneId = ZoneId.systemDefault()
    val zonedDateTime = this.atZone(zoneId)
    val currentYear = Instant.now().atZone(zoneId).year

    val pattern = if (zonedDateTime.year == currentYear) "d MMM" else "d MMM yyyy"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH)

    return zonedDateTime.format(formatter)
}

fun Instant.toNoteUiDateTime(): String {
    val pattern = "dd MMM yyyy, HH:mm"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH)
    return this.atZone(ZoneId.systemDefault()).format(formatter)
}