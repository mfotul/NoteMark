package com.example.notemark.note.presentation.settings

sealed interface SettingsEvent {
    data object OnLogOut : SettingsEvent
}