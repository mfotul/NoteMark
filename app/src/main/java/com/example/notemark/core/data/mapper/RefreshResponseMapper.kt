package com.example.notemark.core.data.mapper

import com.example.notemark.core.data.networking.dto.RefreshResponseDto
import io.ktor.client.plugins.auth.providers.BearerTokens

fun RefreshResponseDto.toBearer(): BearerTokens {
    return BearerTokens(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}