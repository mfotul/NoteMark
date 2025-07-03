package com.example.notemark.note.data.networking

import com.example.notemark.core.data.mapper.toNote
import com.example.notemark.core.data.mapper.toNoteDto
import com.example.notemark.core.data.mapper.toUserPreferences
import com.example.notemark.core.data.networking.constructUrl
import com.example.notemark.core.data.networking.dto.LoginRequestDto
import com.example.notemark.core.data.networking.dto.LoginResponseDto
import com.example.notemark.core.data.networking.dto.NoteDto
import com.example.notemark.core.data.networking.dto.NotesResponse
import com.example.notemark.core.data.networking.dto.RefreshRequestDto
import com.example.notemark.core.data.networking.dto.RegisterRequestDto
import com.example.notemark.core.data.networking.safeCall
import com.example.notemark.core.domain.util.DataError
import com.example.notemark.core.domain.util.Result
import com.example.notemark.core.domain.util.map
import com.example.notemark.note.domain.Note
import com.example.notemark.note.domain.NoteMarkNetworkDataSource
import com.example.notemark.note.domain.DataStoreSettings
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RemoteNoteMarkDataSource(
    private val httpClient: HttpClient
) : NoteMarkNetworkDataSource {
    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): Result<Unit, DataError> {
        val request = RegisterRequestDto(
            username = username,
            email = email,
            password = password
        )
        return safeCall<Unit> {
            httpClient.post(
                urlString = constructUrl("/api/auth/register")
            ) {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }
    }

    override suspend fun login(
        email: String,
        password: String
    ): Result<DataStoreSettings, DataError> {
        val request = LoginRequestDto(
            email = email,
            password = password
        )
        return safeCall<LoginResponseDto> {
            httpClient.post(
                urlString = constructUrl("/api/auth/login")
            ) {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }.map { response ->
            response.toUserPreferences()
        }
    }

    override suspend fun getNotes(): Result<List<Note>, DataError> {
        return safeCall<NotesResponse> {
            httpClient.get(
                urlString = constructUrl("/api/notes?page=-1")
            )
        }.map { response ->
            response.notes.map { noteDto ->
                noteDto.toNote()
            }
        }
    }

    override suspend fun postNote(note: Note): Result<Note, DataError> {
        return safeCall<NoteDto> {
            httpClient.post(
                urlString = constructUrl("/api/notes")
            ) {
                contentType(ContentType.Application.Json)
                setBody(note.toNoteDto())
            }
        }.map { noteDto ->
            noteDto.toNote()
        }
    }

    override suspend fun updateNote(note: Note): Result<Note, DataError> {
        return safeCall<NoteDto> {
            httpClient.put(
                urlString = constructUrl("/api/notes")
            ) {
                contentType(ContentType.Application.Json)
                setBody(note.toNoteDto())
            }
        }.map {noteDto ->
            noteDto.toNote()
        }
    }

    override suspend fun deleteNote(noteId: String): Result<Unit, DataError> {
        return safeCall<Unit> {
            httpClient.delete(
                urlString = constructUrl("/api/notes/$noteId")
            )
        }
    }

    override suspend fun logout(refreshToken: String): Result<Unit, DataError> {
        return safeCall<Unit> {
            httpClient.post(
                urlString = constructUrl("/api/auth/logout")
            ) {
                contentType(ContentType.Application.Json)
                setBody(RefreshRequestDto(refreshToken))
            }
        }
    }
}