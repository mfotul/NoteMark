package com.example.notemark.note.presentation.register

sealed interface RegisterAction {
    data class OnChangeUserName(val username: String): RegisterAction
    data class OnChangeEmail(val email: String): RegisterAction
    data class OnChangePassword(val password: String): RegisterAction
    data class OnChangeRepeatedPassword(val repeatedPassword: String): RegisterAction
    data object OnRegisterClick : RegisterAction
}