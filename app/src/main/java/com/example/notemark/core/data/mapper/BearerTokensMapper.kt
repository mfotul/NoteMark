package com.example.notemark.core.data.mapper

import com.example.notemark.core.data.networking.dto.BearerTokensDto
import com.example.notemark.note.domain.BearerTokens

fun BearerTokensDto.toBearerToken(): BearerTokens {
    return BearerTokens(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}