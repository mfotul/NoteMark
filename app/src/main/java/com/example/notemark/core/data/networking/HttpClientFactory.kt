package com.example.notemark.core.data.networking

import com.example.notemark.BuildConfig
import com.example.notemark.core.data.networking.dto.RefreshTokenDto
import com.example.notemark.core.domain.util.Result
import com.example.notemark.core.domain.util.onSuccess
import com.example.notemark.note.domain.NoteMarkDataStore
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


object HttpClientFactory {
    fun create(engine: HttpClientEngine, dataStore: NoteMarkDataStore): HttpClient {
        return HttpClient(engine) {
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.ANDROID
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val tokenPair = dataStore.get()
                        BearerTokens(
                            accessToken = tokenPair?.accessToken.orEmpty(),
                            refreshToken = tokenPair?.refreshToken.orEmpty()
                        )
                    }
                    refreshTokens {
                        val tokenPair = dataStore.get()
                        val response = safeCall<BearerTokens> {
                            client.post(BuildConfig.BASE_URL + "auth/api/refresh") {
                                contentType(ContentType.Application.Json)
                                setBody(
                                    RefreshTokenDto(
                                        refreshToken = tokenPair?.refreshToken.orEmpty()
                                    )
                                )
                                markAsRefreshTokenRequest()
                                header("Debug", true)
                            }
                        }

                        if (response is Result.Success) {
                            response.onSuccess { tokens ->
                                dataStore.update(tokens)
                            }
                            val tokenPair = dataStore.get()
                            BearerTokens(
                                accessToken = tokenPair?.accessToken.orEmpty(),
                                refreshToken = tokenPair?.refreshToken.orEmpty()
                            )
                        } else {
                            BearerTokens(
                                accessToken = "",
                                refreshToken = ""
                            )
                        }
                    }
                }
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                header("X-User-Email", BuildConfig.USER_EMAIL)
            }
        }
    }
}