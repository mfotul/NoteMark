package com.example.notemark.note.data.networking

import com.example.notemark.core.data.mapper.toBearerToken
import com.example.notemark.core.data.networking.constructUrl
import com.example.notemark.core.data.networking.dto.BearerTokensDto
import com.example.notemark.core.data.networking.dto.LoginRequestDto
import com.example.notemark.core.data.networking.dto.RegisterRequestDto
import com.example.notemark.core.data.networking.safeCall
import com.example.notemark.core.domain.util.NetworkError
import com.example.notemark.core.domain.util.Result
import com.example.notemark.core.domain.util.map
import com.example.notemark.note.domain.BearerTokens
import com.example.notemark.note.domain.NoteMarkDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RemoteNoteMarkDataSource(
    private val httpClient: HttpClient
) : NoteMarkDataSource {
    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): Result<Unit, NetworkError> {
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
    ): Result<BearerTokens, NetworkError> {
        val request = LoginRequestDto(
            email = email,
            password = password
        )
        return safeCall<BearerTokensDto> {
            httpClient.post(
                urlString = constructUrl("/api/auth/login")
            ) {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }.map { response ->
            response.toBearerToken()
        }
    }
}