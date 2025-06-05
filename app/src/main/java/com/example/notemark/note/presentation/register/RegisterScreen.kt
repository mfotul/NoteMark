package com.example.notemark.note.presentation.register

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
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun RegisterScreen(
    isPortrait: Boolean,
    isTablet: Boolean,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(top = if (isPortrait) 40.dp else 20.dp)
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
                RegisterScreenMain(onLoginClick = onLoginClick)
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
                    isTablet = isTablet
                )
                RegisterScreenMain(onLoginClick = onLoginClick)
            }
        }
    }
}


@Composable
fun RegisterScreenMain(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        NoteTextField(
            value = "",
            onValueChange = {},
            label = stringResource(R.string.username),
            placeholder = stringResource(R.string.jon_doe),
            supportingText = stringResource(R.string.username_supporting_text),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        NoteTextField(
            value = "",
            onValueChange = {},
            label = stringResource(R.string.email),
            placeholder = stringResource(R.string.jon_doe_email),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        NoteTextField(
            value = "",
            onValueChange = {},
            label = stringResource(R.string.password),
            placeholder = stringResource(R.string.password),
            supportingText = stringResource(R.string.password_supporting_text),
            isPassword = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        NoteTextField(
            value = "",
            onValueChange = {},
            label = stringResource(R.string.repeat_password),
            placeholder = stringResource(R.string.password),
            isPassword = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(24.dp))
        NoteButton(
            text = stringResource(R.string.create_account),
            onClick = {},
            isEnabled = false,
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
        )
    }
}