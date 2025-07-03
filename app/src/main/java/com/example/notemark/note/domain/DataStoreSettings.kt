package com.example.notemark.note.domain

import kotlinx.serialization.Serializable

@Serializable
data class DataStoreSettings(
    val accessToken: String = "",
    val refreshToken: String? = null,
    val userName: String = "",
)
