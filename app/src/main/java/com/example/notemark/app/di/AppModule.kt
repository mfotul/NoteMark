package com.example.notemark.app.di

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.notemark.app.NoteMarkApp
import com.example.notemark.core.data.datastore.UserPreferencesSerializer
import com.example.notemark.note.data.NoteMarkRepositoryImpl
import com.example.notemark.note.data.datastore.NoteMarkPreferencesDataStore
import com.example.notemark.note.domain.NoteMarkDataStore
import com.example.notemark.note.domain.NoteMarkRepository
import com.example.notemark.note.presentation.edit.EditViewModel
import com.example.notemark.note.presentation.list.ListViewModel
import com.example.notemark.note.presentation.login.LoginViewModel
import com.example.notemark.note.presentation.register.RegisterViewModel
import com.example.notemark.note.presentation.settings.SettingsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single {
        (androidApplication() as NoteMarkApp).applicationScope
    }

    single {
        DataStoreFactory.create(UserPreferencesSerializer) {
            get<Context>().dataStoreFile("user_preferences.pb")
        }
    }

    singleOf(::NoteMarkPreferencesDataStore) bind NoteMarkDataStore::class
    singleOf(::NoteMarkRepositoryImpl) bind NoteMarkRepository::class

    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ListViewModel)
    viewModelOf(::EditViewModel)
    viewModelOf(::SettingsViewModel)
}