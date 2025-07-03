package com.example.notemark.core.data.database.di

import androidx.room.Room
import com.example.notemark.core.data.database.NoteMarkDatabase
import com.example.notemark.note.data.database.RoomNoteMarkDataSource
import com.example.notemark.note.domain.NoteMarkLocalDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single<NoteMarkDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            NoteMarkDatabase::class.java,
            "notemark.db"
        ).build()
    }
    single {
        get<NoteMarkDatabase>().noteMarkDao
    }

    single<NoteMarkLocalDataSource> { RoomNoteMarkDataSource(get()) }
}