package com.example.notemark.note.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notemark.core.domain.util.onError
import com.example.notemark.core.domain.util.onSuccess
import com.example.notemark.core.presentation.util.SnackBarController
import com.example.notemark.core.presentation.util.SnackBarEvent
import com.example.notemark.note.domain.NoteMarkNetworkDataSource
import com.example.notemark.note.domain.NoteMarkDataStore
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val noteMarkDataSource: NoteMarkNetworkDataSource,
    private val noteMarkDataStore: NoteMarkDataStore,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _isLoggedIn = Channel<Boolean>()
    val isLoggedIn = _isLoggedIn.receiveAsFlow()

    fun onEvent(event: LoginAction) {
        when (event) {
            is LoginAction.OnChangeEmail -> changeEmail(event.email)
            is LoginAction.OnChangePassword -> changePassword(event.password)
            LoginAction.OnLoginClick -> login()
        }
    }

    private fun changeEmail(string: String) {
        _state.value = _state.value.copy(
            email = string
        )
    }

    private fun changePassword(string: String) {
        _state.value = _state.value.copy(
            password = string
        )
    }

    private fun login() {
        _state.value = _state.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            noteMarkDataSource.login(
                email = state.value.email,
                password = state.value.password
            ).onSuccess { authData ->
                noteMarkDataStore.update(authData)
            }.onError { error ->
                SnackBarController.sendEvent(
                    SnackBarEvent(
                        message = error
                    )
                )
            }
            _state.value = _state.value.copy(
                isLoading = false
            )
            _isLoggedIn.send(true)
        }
    }
}