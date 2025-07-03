package com.example.notemark.note.presentation.settings

sealed interface SettingsAction {
    data object OnBackClick : SettingsAction
    data object OnLogOutClick : SettingsAction
}