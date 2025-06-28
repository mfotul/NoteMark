package com.example.notemark.core.data.mapper

import com.example.notemark.core.data.networking.dto.LoginResponseDto
import com.example.notemark.note.domain.UserPreferences

fun LoginResponseDto.toUserPreferences(): UserPreferences {
    return UserPreferences(
        accessToken = accessToken,
        refreshToken = refreshToken,
        userName = userName
    )
}