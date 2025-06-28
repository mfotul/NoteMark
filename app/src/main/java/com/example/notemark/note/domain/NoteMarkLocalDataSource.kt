package com.example.notemark.note.domain

import com.example.notemark.core.domain.util.EmptyResult
import com.example.notemark.core.domain.util.DataError
import kotlinx.coroutines.flow.Flow

interface NoteMarkLocalDataSource {
    fun getAllNotes(): Flow<List<Note>>
    fun getNoteById(noteId: String): Flow<Note>
    suspend fun upsertNote(note: Note): EmptyResult<DataError>
    suspend fun deleteNote(note: Note): EmptyResult<DataError>
}