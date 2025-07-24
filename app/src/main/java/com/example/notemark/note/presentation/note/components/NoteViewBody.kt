package com.example.notemark.note.presentation.note.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notemark.R
import com.example.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import com.example.notemark.note.presentation.model.NoteUi
import com.example.notemark.note.presentation.model.toNoteUi
import com.example.notemark.note.presentation.preview.PreviewModels
import java.time.Duration
import java.time.Instant

@Composable
fun NoteViewBody(
    isPortrait: Boolean,
    note: NoteUi,
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier
                .fillMaxHeight()
                .then(
                    if (!isPortrait)
                        Modifier.widthIn(max = 450.dp)
                    else
                        Modifier.fillMaxWidth()
                )
                .padding(horizontal = 16.dp)
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.Start),
                modifier = Modifier.fillMaxWidth()
            ) {
                NoteTimeText(
                    label = "Date created",
                    time = note.createdAtDateTime
                )
                val duration = Duration.between(note.lastEditedAt, Instant.now())
                NoteTimeText(
                    label = "Last Edited",
                    time = if (duration.toMinutes() < 5)
                        stringResource(R.string.just_now)
                    else
                        note.lastEditedAtDateTime
                )
            }
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NoteViewBodyPreview() {
    NoteMarkTheme {
        NoteViewBody(
            isPortrait = true,
            note = PreviewModels.note.toNoteUi(),
        )
    }
}
