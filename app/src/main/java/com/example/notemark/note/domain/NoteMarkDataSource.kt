package com.example.notemark.note.domain

import com.example.notemark.core.domain.util.NetworkError
import com.example.notemark.core.domain.util.Result


interface NoteMarkDataSource {
    suspend fun register(
        username: String,
        email: String,
        password: String
    ): Result<Unit, NetworkError>

    suspend fun login(
        email: String,
        password: String
    ): Result<BearerTokens, NetworkError>
}
