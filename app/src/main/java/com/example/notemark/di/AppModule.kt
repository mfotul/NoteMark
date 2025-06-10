package com.example.notemark.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.notemark.core.data.datastore.UserPreferencesSerializer
import com.example.notemark.core.data.networking.HttpClientFactory
import com.example.notemark.note.data.datastore.UserPreferencesDataStore
import com.example.notemark.note.data.networking.RemoteNoteMarkDataSource
import com.example.notemark.note.domain.NoteMarkDataSource
import com.example.notemark.note.domain.NoteMarkDataStore
import com.example.notemark.note.domain.UserPreferences
import com.example.notemark.note.presentation.login.LoginViewModel
import com.example.notemark.note.presentation.register.RegisterViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single {
        DataStoreFactory.create(UserPreferencesSerializer) {
            get<Context>().dataStoreFile("user_preferences.pb")
        }
    }
    singleOf(::UserPreferencesDataStore).bind<NoteMarkDataStore>()
    single { HttpClientFactory.create(CIO.create(), get()) }
    singleOf(::RemoteNoteMarkDataSource).bind<NoteMarkDataSource>()

    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
}