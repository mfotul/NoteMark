package com.example.notemark.note.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBars
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notemark.R
import com.example.notemark.core.domain.util.DataError
import com.example.notemark.core.presentation.designsystem.buttons.NoteButton
import com.example.notemark.core.presentation.designsystem.text_fields.NoteTextField
import com.example.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import com.example.notemark.core.presentation.util.ObserveAsEvent
import com.example.notemark.core.presentation.util.SnackBarController
import com.example.notemark.core.presentation.util.toString
import com.example.notemark.note.presentation.components.LoginRegisterTop
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    isPortrait: Boolean,
    isTablet: Boolean,
    onLogin: () -> Unit,
    onRegisterClick: () -> Unit,
) {
    val viewModel: LoginViewModel = koinViewModel()
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.isLoggedIn) {
        if (it) onLogin()
    }

    LoginScreen(
        isPortrait = isPortrait,
        isTablet = isTablet,
        onRegisterClick = onRegisterClick,
        email = uiState.email,
        password = uiState.password,
        isLoading = uiState.isLoading,
        onEvent = viewModel::onEvent
    )
}


@Composable
fun LoginScreen(
    isPortrait: Boolean,
    isTablet: Boolean,
    email: String,
    password: String,
    isLoading: Boolean,
    onEvent: (LoginAction) -> Unit,
    onRegisterClick: () -> Unit,
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
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(padding)
                .padding(top = 8.dp)
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
                        title = stringResource(R.string.log_in),
                        isTablet = isTablet
                    )
                    Spacer(modifier = Modifier.height(48.dp))
                    LoginScreenMain(
                        email = email,
                        password = password,
                        isLoading = isLoading,
                        onEvent = onEvent,
                        onRegisterClick = onRegisterClick,
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
                        title = stringResource(R.string.log_in),
                        isTablet = isTablet,
                        modifier = Modifier.weight(1f)
                    )
                    LoginScreenMain(
                        email = email,
                        password = password,
                        isLoading = isLoading,
                        onEvent = onEvent,
                        onRegisterClick = onRegisterClick,
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    )
                }
            }
        }
    }
}


@Composable
fun LoginScreenMain(
    email: String,
    password: String,
    onEvent: (LoginAction) -> Unit,
    isLoading: Boolean,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val isLoginButtonEnabled by remember(email, password) {
        derivedStateOf {
            email.isNotEmpty() && password.isNotEmpty()
        }
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .imePadding()
    ) {
        NoteTextField(
            value = email,
            onValueChange = { onEvent(LoginAction.OnChangeEmail(it)) },
            label = stringResource(R.string.email),
            placeholder = stringResource(R.string.jon_doe_email),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(16.dp))
        NoteTextField(
            value = password,
            onValueChange = { onEvent(LoginAction.OnChangePassword(it)) },
            label = stringResource(R.string.password),
            placeholder = stringResource(R.string.password),
            isPassword = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(24.dp))
        NoteButton(
            text = stringResource(R.string.log_in),
            onClick = {
                keyboardController?.hide()
                onEvent(LoginAction.OnLoginClick)
            },
            isLoading = isLoading,
            isEnabled = isLoginButtonEnabled,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.dont_have_account),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    onRegisterClick()
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    NoteMarkTheme {
        LoginScreen(
            isPortrait = true,
            isTablet = false,
            email = "",
            password = "",
            isLoading = false,
            onEvent = {},
            onRegisterClick = {},
        )
    }
}

@Composable
@Preview(showBackground = true, widthDp = 900, heightDp = 500)
fun LoginScreenPreviewLandscape() {
    NoteMarkTheme {
        LoginScreen(
            isPortrait = false,
            isTablet = false,
            email = "",
            password = "",
            isLoading = false,
            onEvent = {},
            onRegisterClick = { },
        )
    }
}

@Composable
@Preview(showBackground = true, widthDp = 1000, heightDp = 1500)
fun LoginScreenPreviewTablet() {
    NoteMarkTheme {
        LoginScreen(
            email = "",
            password = "",
            onEvent = {},
            isLoading = false,
            isPortrait = true,
            isTablet = true,
            onRegisterClick = { },
        )
    }
}