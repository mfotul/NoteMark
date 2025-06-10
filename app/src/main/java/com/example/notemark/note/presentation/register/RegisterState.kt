package com.example.notemark.note.presentation.register

data class RegisterState(
    val userName: String = "",
    val userNameHasError: Boolean = false,
    val email: String = "",
    val emailHasError: Boolean = false,
    val password: String = "",
    val passwordHasError: Boolean = false,
    val repeatedPassword: String = "",
    val repeatedPasswordHasError: Boolean = false,
    val isLoading: Boolean = false,
)
