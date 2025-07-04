package com.example.notemark.core.presentation.designsystem.text_fields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notemark.R
import com.example.notemark.core.presentation.designsystem.theme.NoteMarkTheme

@Composable
fun NoteTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    isError: Boolean = false,
    errorText: String? = null,
    supportingText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    var isFocused by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.alpha(if (isFocused) 0.7f else 1f)
                )
            },
            supportingText = {
                if (supportingText != null && isFocused) {
                    TextFieldSupportingText(
                        text = supportingText,
                        isError = false
                    )
                } else if (errorText != null && isError && !isFocused) {
                    TextFieldSupportingText(
                        text = errorText,
                        isError = true
                    )
                }
            },
            visualTransformation = if (isPassword && !passwordVisible)
                PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                if (isPassword) {
                    val imageResId =
                        if (passwordVisible) R.drawable.eye_off else R.drawable.eye
                    val description = if (passwordVisible)
                        stringResource(R.string.hide_password)
                    else stringResource(R.string.show_password)

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(id = imageResId),
                            contentDescription = description
                        )
                    }
                }
            },
            singleLine = true,
            isError = isError && !isFocused,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.primary,
                errorCursorColor = MaterialTheme.colorScheme.error,
                errorBorderColor = MaterialTheme.colorScheme.error,
            ),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                }
        )
    }
}

@Composable
fun TextFieldSupportingText(
    text: String,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = if (isError)
            MaterialTheme.colorScheme.error
        else
            MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
    )
}

@Composable
@Preview(showBackground = true)
fun NoteTextFieldPreview() {
    NoteMarkTheme {
        NoteTextField(
            value = "",
            onValueChange = {},
            label = "Email",
            placeholder = "Jon Doe Email"
        )
    }
}