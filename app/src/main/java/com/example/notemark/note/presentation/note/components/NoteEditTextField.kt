package com.example.notemark.note.presentation.note.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.notemark.core.presentation.designsystem.theme.NoteMarkTheme

@Composable
fun NoteEditTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    singleLine: Boolean,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    placeHolder: @Composable (() -> Unit)? = null,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        textStyle = textStyle,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = placeHolder,
        modifier = modifier,
    )
}

@Composable
@Preview(showBackground = true)
fun NoteEditTextFieldPreview() {
    NoteMarkTheme {
        NoteEditTextField(
            value = TextFieldValue("test"),
            onValueChange = {},
            singleLine = false,
            textStyle = MaterialTheme.typography.titleLarge,
        )
    }
}