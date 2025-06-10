package com.example.notemark.note.data.mappers

import com.example.notemark.note.domain.BearerTokens
import io.ktor.client.plugins.auth.providers.BearerTokens as KoinBearerTokens

fun BearerTokens.toKoinBearerToken(): KoinBearerTokens {
    return KoinBearerTokens(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}