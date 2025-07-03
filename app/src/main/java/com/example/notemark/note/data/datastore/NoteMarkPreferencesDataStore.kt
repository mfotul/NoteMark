package com.example.notemark.note.data.datastore

import androidx.datastore.core.DataStore
import com.example.notemark.note.domain.NoteMarkDataStore
import com.example.notemark.note.domain.DataStoreSettings
import io.ktor.client.plugins.auth.providers.BearerTokens
import kotlinx.coroutines.flow.Flow

class NoteMarkPreferencesDataStore(
    private val preferencesDataStore: DataStore<DataStoreSettings>
) : NoteMarkDataStore {

    override fun getSettings(): Flow<DataStoreSettings?> {
        return preferencesDataStore.data
    }

    override suspend fun update(response: DataStoreSettings) {
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

    override suspend fun clear() {
        preferencesDataStore.updateData {
            DataStoreSettings()
        }
    }
}