package com.example.notemark.note.presentation.login

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notemark.R
import com.example.notemark.note.presentation.components.LoginRegisterTop
import com.example.notemark.note.presentation.components.NoteButton
import com.example.notemark.note.presentation.components.NoteTextField
import com.example.notemark.ui.theme.NoteMarkTheme

@Composable
fun LoginScreen(
    isPortrait: Boolean,
    isTablet: Boolean,
    email: String,
    password: String,
    onEvent: (LoginEvent) -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold { padding ->
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
                        title = stringResource(R.string.log_in),
                        isTablet = isTablet
                    )
                    Spacer(modifier = Modifier.height(48.dp))
                    LoginScreenMain(
                        email = email,
                        password = password,
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
    onEvent: (LoginEvent) -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val isLoginButtonEnabled by remember(email, password) {
        derivedStateOf {
            email.isNotEmpty() && password.isNotEmpty()
        }
    }
    
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        NoteTextField(
            value = email,
            onValueChange = { onEvent(LoginEvent.ChangeEmail(it)) },
            label = stringResource(R.string.email),
            placeholder = stringResource(R.string.jon_doe_email),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(16.dp))
        NoteTextField(
            value = password,
            onValueChange = { onEvent(LoginEvent.ChangePassword(it)) },
            label = stringResource(R.string.password),
            placeholder = stringResource(R.string.password),
            isPassword = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(24.dp))
        NoteButton(
            text = stringResource(R.string.log_in),
            onClick = { onEvent(LoginEvent.Login) },
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
            isTablet = true,
            email = "",
            password = "",
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
            isPortrait = true,
            isTablet = true,
            onRegisterClick = { },
        )
    }
}