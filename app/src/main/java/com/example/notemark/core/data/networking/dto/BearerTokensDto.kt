package com.example.notemark.core.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class BearerTokensDto(
    val accessToken: String,
    val refreshToken: String,
)
