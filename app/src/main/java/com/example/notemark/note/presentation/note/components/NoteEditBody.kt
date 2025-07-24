package com.example.notemark.note.presentation.note.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notemark.R
import com.example.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import com.example.notemark.note.presentation.note.NoteAction

@Composable
fun NoteEditBody(
    isPortrait: Boolean,
    title: TextFieldValue,
    content: TextFieldValue,
    focusRequester: FocusRequester,
    onAction: (NoteAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxHeight()
                .then(
                    if (!isPortrait)
                        Modifier.widthIn(max = 450.dp)
                    else
                        Modifier.fillMaxWidth()
                )
                .align(Alignment.TopCenter)
                .imePadding()
                .verticalScroll(rememberScrollState())
        ) {
            NoteEditTextField(
                value = title,
                onValueChange = { onAction(NoteAction.OnChangeTitle(it)) },
                singleLine = true,
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                placeHolder = {
                    Text(
                        text = stringResource(R.string.title),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            NoteEditTextField(
                value = content,
                onValueChange = { onAction(NoteAction.OnChangeContent(it)) },
                singleLine = false,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = false)
fun NoteEditBodyPreview() {
    NoteMarkTheme {
        NoteEditBody(
            isPortrait = true,
            title = TextFieldValue("Title"),
            content = TextFieldValue("Content"),
            focusRequester = FocusRequester(),
            onAction = {},
        )
    }
}