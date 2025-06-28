package com.example.notemark.note.domain

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val accessToken: String = "",
    val refreshToken: String? = null,
    val userName: String = "",
)
