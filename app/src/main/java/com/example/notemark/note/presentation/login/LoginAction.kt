package com.example.notemark.note.presentation.login

sealed interface LoginAction {
    data class OnChangeEmail(val email: String): LoginAction
    data class OnChangePassword(val password: String): LoginAction
    data object OnLoginClick : LoginAction
}