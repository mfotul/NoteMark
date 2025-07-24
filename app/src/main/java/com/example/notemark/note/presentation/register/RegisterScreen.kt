package com.example.notemark.note.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notemark.R
import com.example.notemark.core.domain.util.DataError
import com.example.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import com.example.notemark.core.presentation.util.ObserveAsEvent
import com.example.notemark.core.presentation.util.SnackBarController
import com.example.notemark.core.presentation.util.toString
import com.example.notemark.note.presentation.components.LoginRegisterTop
import com.example.notemark.note.presentation.register.components.RegisterScreenForm
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    isPortrait: Boolean,
    isTablet: Boolean,
    onRegister: () -> Unit,
    onLoginClick: () -> Unit,
) {
    val viewModel: RegisterViewModel = koinViewModel()
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.isRegistered) {
        onRegister()
    }

    RegisterScreen(
        onLoginClick = onLoginClick,
        isPortrait = isPortrait,
        isTablet = isTablet,
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun RegisterScreen(
    isPortrait: Boolean,
    isTablet: Boolean,
    uiState: RegisterState,
    onEvent: (RegisterAction) -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    ObserveAsEvent(SnackBarController.events, snackbarHostState) { event ->
        scope.launch {
            when (event.message) {
                is DataError -> {
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
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.safeContentPadding()
            )
        },
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.statusBars
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(padding)
                .padding(top = 8.dp)
        ) {
            if (isPortrait) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(48.dp),
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
                    RegisterScreenForm(
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
                            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                        )
                        .padding(48.dp)
                ) {
                    LoginRegisterTop(
                        title = stringResource(R.string.create_account),
                        isTablet = isTablet,
                        modifier = Modifier.weight(1f)
                    )
                    RegisterScreenForm(
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
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
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