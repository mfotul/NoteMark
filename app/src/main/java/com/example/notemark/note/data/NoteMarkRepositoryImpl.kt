package com.example.notemark.note.data

import com.example.notemark.core.domain.util.DataError
import com.example.notemark.core.domain.util.EmptyResult
import com.example.notemark.core.domain.util.Result
import com.example.notemark.core.domain.util.asEmptyDataResult
import com.example.notemark.core.domain.util.onSuccess
import com.example.notemark.note.domain.Note
import com.example.notemark.note.domain.NoteMarkLocalDataSource
import com.example.notemark.note.domain.NoteMarkNetworkDataSource
import com.example.notemark.note.domain.NoteMarkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteMarkRepositoryImpl(
    private val noteMarkLocalDataSource: NoteMarkLocalDataSource,
    private val noteMarkNetworkDataSource: NoteMarkNetworkDataSource,
    private val applicationScope: CoroutineScope
): NoteMarkRepository {
    override fun getAllNotes(): Flow<List<Note>> {
        applicationScope.launch {
            noteMarkNetworkDataSource.getNotes(0, 20).onSuccess { notes ->
                notes.forEach { noteMarkLocalDataSource.upsertNote(it) }
            }
        }
        return noteMarkLocalDataSource.getAllNotes()
    }

    override fun getNoteById(noteId: String): Flow<Note> {
        return noteMarkLocalDataSource.getNoteById(noteId)
    }

    override suspend fun insertNote(note: Note): EmptyResult<DataError> {
        val localResult = noteMarkLocalDataSource.upsertNote(note)
        if (localResult !is Result.Success)
            return localResult

        return applicationScope.async {
            val remoteResult = noteMarkNetworkDataSource.postNote(note)
            if (remoteResult is Result.Error)
                return@async remoteResult
            return@async remoteResult.asEmptyDataResult()
        }.await()
    }

    override suspend fun updateNote(note: Note): EmptyResult<DataError> {
        val localResult = noteMarkLocalDataSource.upsertNote(note)
        if (localResult !is Result.Success)
            return localResult

        return applicationScope.async {
            val remoteResult = noteMarkNetworkDataSource.updateNote(note)
            if (remoteResult is Result.Error)
                return@async remoteResult
            return@async remoteResult.asEmptyDataResult()
        }.await()
    }

    override suspend fun deleteNote(note: Note): EmptyResult<DataError> {
        val localResult = noteMarkLocalDataSource.deleteNote(note)
        if (localResult !is Result.Success)
            return localResult
        return applicationScope.async {
            val remoteResult = noteMarkNetworkDataSource.deleteNote(note.id)
            if (remoteResult is Result.Error)
                return@async remoteResult
            return@async remoteResult.asEmptyDataResult()
        }.await()
    }
}