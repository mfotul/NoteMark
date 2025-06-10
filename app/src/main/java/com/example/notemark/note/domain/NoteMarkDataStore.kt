package com.example.notemark.note.domain

import io.ktor.client.plugins.auth.providers.BearerTokens

interface NoteMarkDataStore {
    suspend fun get(): BearerTokens?

    suspend fun update(response: BearerTokens)

}