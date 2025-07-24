package com.example.notemark.note.presentation.register.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.notemark.R
import com.example.notemark.core.presentation.designsystem.buttons.NoteButton
import com.example.notemark.core.presentation.designsystem.text_fields.NoteTextField
import com.example.notemark.note.presentation.register.RegisterAction

@Composable
fun RegisterScreenForm(
    username: String,
    hasUsernameError: Boolean,
    email: String,
    hasEmailError: Boolean,
    password: String,
    hasPasswordError: Boolean,
    repeatedPassword: String,
    hasRepeatedPasswordError: Boolean,
    isLoading: Boolean,
    onEvent: (RegisterAction) -> Unit,
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

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .imePadding()
    ) {
        NoteTextField(
            value = username,
            onValueChange = { onEvent(RegisterAction.OnChangeUserName(it)) },
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
            onValueChange = { onEvent(RegisterAction.OnChangeEmail(it)) },
            label = stringResource(R.string.email),
            placeholder = stringResource(R.string.jon_doe_email),
            isError = hasEmailError,
            errorText = stringResource(R.string.invalid_email),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        NoteTextField(
            value = password,
            onValueChange = { onEvent(RegisterAction.OnChangePassword(it)) },
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
            onValueChange = { onEvent(RegisterAction.OnChangeRepeatedPassword(it)) },
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
            onClick = {
                keyboardController?.hide()
                onEvent(RegisterAction.OnRegisterClick)
            },
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
