package com.example.notemark.note.presentation.register

sealed interface RegisterEvent {
    data class ChangeUserName(val username: String): RegisterEvent
    data class ChangeEmail(val email: String): RegisterEvent
    data class ChangePassword(val password: String): RegisterEvent
    data class ChangeRepeatedPassword(val repeatedPassword: String): RegisterEvent
    object Register : RegisterEvent
}