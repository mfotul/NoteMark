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

class NoteMarkRepositoryImpl(
    private val noteMarkLocalDataSource: NoteMarkLocalDataSource,
    private val noteMarkNetworkDataSource: NoteMarkNetworkDataSource,
    private val applicationScope: CoroutineScope
) : NoteMarkRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return noteMarkLocalDataSource.getAllNotes()
    }

    override fun getNoteById(noteId: String): Flow<Note> {
        return noteMarkLocalDataSource.getNoteById(noteId)
    }

    override suspend fun syncNotes(): EmptyResult<DataError> {
        return applicationScope.async {
            val result = noteMarkNetworkDataSource.getNotes()
                .onSuccess { notes ->
                    notes.forEach { noteMarkLocalDataSource.upsertNote(it) }
                }
            if (result is Result.Error)
                return@async result
            return@async result.asEmptyDataResult()
        }.await()
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

    override suspend fun logoutUser(refreshToken: String): EmptyResult<DataError> {
        val localResult = noteMarkLocalDataSource.deleteAllNotes()
        if (localResult !is Result.Success)
            return localResult
        return applicationScope.async {
            val remoteResult = noteMarkNetworkDataSource.logout(refreshToken)
            if (remoteResult is Result.Error)
                return@async remoteResult
            return@async remoteResult.asEmptyDataResult()
        }.await()

    }
}