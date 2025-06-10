package com.example.notemark.note.data.datastore

import androidx.datastore.core.DataStore
import com.example.notemark.note.domain.NoteMarkDataStore
import com.example.notemark.note.domain.UserPreferences
import io.ktor.client.plugins.auth.providers.BearerTokens
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class UserPreferencesDataStore(
    private val preferencesDataStore: DataStore<UserPreferences>
) : NoteMarkDataStore {
    val userPreferences = preferencesDataStore.data

    override suspend fun get(): BearerTokens? {
        return userPreferences.map {
            BearerTokens(
                accessToken = it.accessToken,
                refreshToken = it.refreshToken
            )
        }.firstOrNull()
    }

    override suspend fun update(response: BearerTokens) {
        preferencesDataStore.updateData { preferences ->
            preferences.copy(
                accessToken = response.accessToken,
                refreshToken = response.refreshToken ?: ""
            )
        }
    }
}