package com.example.notemark.core.data.networking.di

import com.example.notemark.core.data.networking.HttpClientFactory
import com.example.notemark.note.data.networking.RemoteNoteMarkDataSource
import com.example.notemark.note.domain.NoteMarkNetworkDataSource
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    single { HttpClientFactory.create(CIO.create(), get()) }
    single<NoteMarkNetworkDataSource> { RemoteNoteMarkDataSource(get()) }

    singleOf(::RemoteNoteMarkDataSource) bind NoteMarkNetworkDataSource::class
}