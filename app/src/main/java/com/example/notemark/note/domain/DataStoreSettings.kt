package com.example.notemark.note.domain

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class DataStoreSettings(
    val accessToken: String = "",
    val refreshToken: String? = null,
    val userName: String = "",
)
