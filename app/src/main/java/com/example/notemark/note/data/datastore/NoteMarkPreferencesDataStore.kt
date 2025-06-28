package com.example.notemark.note.data.datastore

import androidx.datastore.core.DataStore
import com.example.notemark.note.domain.NoteMarkDataStore
import com.example.notemark.note.domain.UserPreferences
import io.ktor.client.plugins.auth.providers.BearerTokens
import kotlinx.coroutines.flow.firstOrNull

class NoteMarkPreferencesDataStore(
    private val preferencesDataStore: DataStore<UserPreferences>
) : NoteMarkDataStore {
    val userPreferences = preferencesDataStore.data

    override suspend fun get(): UserPreferences? {
        return userPreferences.firstOrNull()
    }

    override suspend fun update(response: UserPreferences) {
        preferencesDataStore.updateData { preferences ->
            preferences.copy(
                accessToken = response.accessToken,
                refreshToken = response.refreshToken,
                userName = response.userName
            )
        }
    }

    override suspend fun updateTokens(tokens: BearerTokens) {
        preferencesDataStore.updateData { preferences ->
            preferences.copy(
                accessToken = tokens.accessToken,
                refreshToken = tokens.refreshToken
            )
        }
    }
}