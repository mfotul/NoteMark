package com.example.notemark.note.domain

import io.ktor.client.plugins.auth.providers.BearerTokens

interface NoteMarkDataStore {
    suspend fun get(): UserPreferences?

    suspend fun update(response: UserPreferences)

    suspend fun updateTokens(tokens: BearerTokens)
}