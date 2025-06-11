package com.example.notemark.note.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notemark.R
import com.example.notemark.core.domain.util.NetworkError
import com.example.notemark.core.presentation.util.ObserveAsEvent
import com.example.notemark.core.presentation.util.SnackBarController
import com.example.notemark.core.presentation.util.toString
import com.example.notemark.note.presentation.components.LoginRegisterTop
import com.example.notemark.note.presentation.components.NoteButton
import com.example.notemark.note.presentation.components.NoteTextField
import com.example.notemark.ui.theme.NoteMarkTheme
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    isPortrait: Boolean,
    isTablet: Boolean,
    uiState: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    ObserveAsEvent(SnackBarController.events, snackbarHostState) { event ->
        scope.launch {
            when (event.message) {
                is NetworkError -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.toString(context),
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { padding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(padding)
        ) {
            if (isPortrait) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainerLowest,
                            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                        )
                        .fillMaxSize()
                        .padding(if (isTablet) 124.dp else 16.dp)
                ) {
                    LoginRegisterTop(
                        title = stringResource(R.string.create_account),
                        isTablet = isTablet
                    )
                    Spacer(modifier = Modifier.height(48.dp))
                    RegisterScreenMain(
                        onLoginClick = onLoginClick,
                        username = uiState.userName,
                        hasUsernameError = uiState.userNameHasError,
                        email = uiState.email,
                        hasEmailError = uiState.emailHasError,
                        password = uiState.password,
                        hasPasswordError = uiState.passwordHasError,
                        repeatedPassword = uiState.repeatedPassword,
                        hasRepeatedPasswordError = uiState.repeatedPasswordHasError,
                        isLoading = uiState.isLoading,
                        onEvent = onEvent,
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainerLowest,
                            shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
                        )
                        .padding(48.dp)
                ) {
                    LoginRegisterTop(
                        title = stringResource(R.string.create_account),
                        isTablet = isTablet,
                        modifier = Modifier.weight(1f)
                    )
                    RegisterScreenMain(
                        onLoginClick = onLoginClick,
                        username = uiState.userName,
                        hasUsernameError = uiState.userNameHasError,
                        email = uiState.email,
                        hasEmailError = uiState.emailHasError,
                        password = uiState.password,
                        hasPasswordError = uiState.passwordHasError,
                        repeatedPassword = uiState.repeatedPassword,
                        hasRepeatedPasswordError = uiState.repeatedPasswordHasError,
                        isLoading = uiState.isLoading,
                        onEvent = onEvent,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun RegisterScreenMain(
    username: String,
    hasUsernameError: Boolean,
    email: String,
    hasEmailError: Boolean,
    password: String,
    hasPasswordError: Boolean,
    repeatedPassword: String,
    hasRepeatedPasswordError: Boolean,
    isLoading: Boolean,
    onEvent: (RegisterEvent) -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hasError by remember(
        listOf(
            hasUsernameError,
            hasEmailError,
            hasPasswordError,
            hasRepeatedPasswordError
        )
    ) {
        derivedStateOf {
            hasPasswordError || hasUsernameError || hasEmailError || hasRepeatedPasswordError
        }
    }
    val isNotEmpty by remember(
        listOf(
            username,
            email,
            password,
            repeatedPassword
        )
    ) {
        derivedStateOf {
            username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && repeatedPassword.isNotEmpty()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        NoteTextField(
            value = username,
            onValueChange = { onEvent(RegisterEvent.ChangeUserName(it)) },
            label = stringResource(R.string.username),
            placeholder = stringResource(R.string.jon_doe),
            supportingText = stringResource(R.string.username_supporting_text),
            isError = hasUsernameError,
            errorText = if (username.length < 3)
                stringResource(R.string.username_short)
            else if (username.length > 20)
                stringResource(R.string.username_long)
            else "",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        NoteTextField(
            value = email,
            onValueChange = { onEvent(RegisterEvent.ChangeEmail(it)) },
            label = stringResource(R.string.email),
            placeholder = stringResource(R.string.jon_doe_email),
            isError = hasEmailError,
            errorText = stringResource(R.string.invalid_email),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        NoteTextField(
            value = password,
            onValueChange = { onEvent(RegisterEvent.ChangePassword(it)) },
            label = stringResource(R.string.password),
            placeholder = stringResource(R.string.password),
            supportingText = stringResource(R.string.password_supporting_text),
            isError = hasPasswordError,
            errorText = stringResource(R.string.password_short),
            isPassword = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        NoteTextField(
            value = repeatedPassword,
            onValueChange = { onEvent(RegisterEvent.ChangeRepeatedPassword(it)) },
            label = stringResource(R.string.repeat_password),
            placeholder = stringResource(R.string.password),
            isError = hasRepeatedPasswordError,
            errorText = stringResource(R.string.password_mismatch),
            isPassword = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(24.dp))
        NoteButton(
            text = stringResource(R.string.create_account),
            onClick = { onEvent(RegisterEvent.Register) },
            isEnabled = isNotEmpty && !hasError,
            isLoading = isLoading,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.already_have_account),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    onLoginClick()
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RegisterScreenPreview() {
    NoteMarkTheme {
        RegisterScreen(
            isPortrait = true,
            isTablet = false,
            onLoginClick = {},
            uiState = RegisterState(),
            onEvent = {}
        )
    }
}

@Composable
@Preview(showBackground = true, widthDp = 900, heightDp = 500)
fun RegisterScreenPreviewLandscape() {
    NoteMarkTheme {
        RegisterScreen(
            isPortrait = false,
            isTablet = false,
            onLoginClick = { },
            uiState = RegisterState(),
            onEvent = {}
        )
    }
}

@Composable
@Preview(showBackground = true, widthDp = 1000, heightDp = 1500)
fun RegisterScreenPreviewTablet() {
    NoteMarkTheme {
        RegisterScreen(
            isPortrait = true,
            isTablet = true,
            onLoginClick = { },
            uiState = RegisterState(),
            onEvent = {}
        )
    }
}