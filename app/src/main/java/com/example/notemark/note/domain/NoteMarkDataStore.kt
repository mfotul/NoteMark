package com.example.notemark.note.domain

import io.ktor.client.plugins.auth.providers.BearerTokens
import kotlinx.coroutines.flow.Flow

interface NoteMarkDataStore {
    fun getSettings(): Flow<DataStoreSettings?>

    suspend fun update(response: DataStoreSettings)

    suspend fun updateTokens(tokens: BearerTokens)

    suspend fun clear()
}