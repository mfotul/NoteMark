package com.example.notemark.note.presentation.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notemark.core.domain.util.onError
import com.example.notemark.core.domain.util.onSuccess
import com.example.notemark.core.presentation.util.SnackBarController
import com.example.notemark.core.presentation.util.SnackBarEvent
import com.example.notemark.note.domain.NoteMarkNetworkDataSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val noteMarkDataSource: NoteMarkNetworkDataSource,
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val _isRegistered = Channel<Boolean>()
    val isRegistered = _isRegistered.receiveAsFlow()

    fun onEvent(event: RegisterAction) {
        when (event) {
            is RegisterAction.OnChangeEmail -> changeEmail(event.email)
            is RegisterAction.OnChangePassword -> changePassword(event.password)
            is RegisterAction.OnChangeRepeatedPassword -> changeRepeatedPassword(event.repeatedPassword)
            is RegisterAction.OnChangeUserName -> changeUserName(event.username)
            RegisterAction.OnRegisterClick -> register()
        }
    }

    private fun changeUserName(username: String) {
        val hasError = username.isNotEmpty() && (username.length < 3 || username.length > 20)
        _state.value = _state.value.copy(
            userName = username,
            userNameHasError = hasError
        )

    }

    private fun changeEmail(email: String) {
        val hasError = email.isNotEmpty()
                && !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        _state.value = _state.value.copy(
            email = email,
            emailHasError = hasError
        )

    }

    private fun changePassword(password: String) {
        val hasError = password.isNotEmpty() && !isPasswordValid(password)

        _state.value = _state.value.copy(
            password = password,
            passwordHasError = hasError
        )

    }

    private fun changeRepeatedPassword(password: String) {
        val hasError = password.isNotEmpty() && password != state.value.password
        _state.value = _state.value.copy(
            repeatedPassword = password,
            repeatedPasswordHasError = hasError
        )

    }

    private fun register() {
        _state.value = _state.value.copy(
            isLoading = true,
        )
        viewModelScope.launch {
            noteMarkDataSource.register(
                username = state.value.userName,
                email = state.value.email,
                password = state.value.password,
            ).onSuccess {
                _isRegistered.send(true)
            }.onError { error ->
                SnackBarController.sendEvent(
                    SnackBarEvent(
                        message = error
                    )
                )
            }
            _state.value = _state.value.copy(
                isLoading = false,
            )
        }
    }


    private fun isPasswordValid(password: String): Boolean {
        val passwordRegex = Regex("^(?=.*[0-9!@#\$%^&*])[A-Za-z0-9!@#\$%^&*]{8,}$")
        return passwordRegex.matches(password)
    }
}