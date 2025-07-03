package com.example.notemark.core.data.mapper

import com.example.notemark.core.data.networking.dto.LoginResponseDto
import com.example.notemark.note.domain.DataStoreSettings

fun LoginResponseDto.toUserPreferences(): DataStoreSettings {
    return DataStoreSettings(
        accessToken = accessToken,
        refreshToken = refreshToken,
        userName = userName
    )
}