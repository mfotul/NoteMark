package com.example.notemark.note.presentation.login

sealed interface LoginEvent {
    data class ChangeEmail(val email: String): LoginEvent
    data class ChangePassword(val password: String): LoginEvent
    object Login : LoginEvent
}