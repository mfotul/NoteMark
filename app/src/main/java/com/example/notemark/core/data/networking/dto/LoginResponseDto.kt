package com.example.notemark.core.data.networking.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
    @SerialName("username")
    val userName: String
)
