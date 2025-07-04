package com.example.notemark.note.domain

import com.example.notemark.core.domain.util.DataError
import com.example.notemark.core.domain.util.Result


interface NoteMarkNetworkDataSource {
    suspend fun register(
        username: String,
        email: String,
        password: String
    ): Result<Unit, DataError>

    suspend fun login(
        email: String,
        password: String
    ): Result<DataStoreSettings, DataError>

    suspend fun getNotes(): Result<List<Note>, DataError>

    suspend fun postNote(note: Note): Result<Note, DataError>

    suspend fun updateNote(note: Note): Result<Note, DataError>

    suspend fun deleteNote(noteId: String): Result<Unit, DataError>

    suspend fun logout(refreshToken: String): Result<Unit, DataError>
}
