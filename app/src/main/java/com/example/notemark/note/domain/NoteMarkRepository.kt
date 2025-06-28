package com.example.notemark.note.domain

import com.example.notemark.core.domain.util.DataError
import com.example.notemark.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface NoteMarkRepository {
    fun getAllNotes(): Flow<List<Note>>
    fun getNoteById(noteId: String): Flow<Note>
    suspend fun insertNote(note: Note): EmptyResult<DataError>
    suspend fun updateNote(note: Note): EmptyResult<DataError>
    suspend fun deleteNote(note: Note): EmptyResult<DataError>
}