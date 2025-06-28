package com.example.notemark.note.data.database

import com.example.notemark.core.database.notemark.NoteMarkDao
import com.example.notemark.core.domain.util.EmptyResult
import com.example.notemark.core.domain.util.DataError
import com.example.notemark.core.domain.util.Result
import com.example.notemark.note.domain.Note
import com.example.notemark.note.domain.NoteMarkLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomNoteMarkDataSource(
    private val noteMarkDao: NoteMarkDao
) : NoteMarkLocalDataSource {
    override fun getAllNotes(): Flow<List<Note>> {
        return noteMarkDao
            .getAllNotes()
            .map { notesEntity ->
                notesEntity.map { noteEntity ->
                    noteEntity.toNote()
                }
            }
    }

    override fun getNoteById(noteId: String): Flow<Note> {
        return noteMarkDao
            .getNoteById(noteId)
            .map { noteEntity ->
                noteEntity.toNote()
            }
    }

    override suspend fun upsertNote(note: Note): EmptyResult<DataError> {
        noteMarkDao.upsert(note.toNoteMarkEntity())
        return Result.Success(Unit)
    }

    override suspend fun deleteNote(note: Note): EmptyResult<DataError> {
        val result = noteMarkDao.delete(note.toNoteMarkEntity())
        return if (result == 1) Result.Success(Unit) else Result.Error(DataError.DATABASE_ERROR)
    }
}