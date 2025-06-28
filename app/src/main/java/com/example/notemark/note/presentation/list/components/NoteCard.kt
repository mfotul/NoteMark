package com.example.notemark.note.presentation.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import com.example.notemark.note.presentation.model.NoteUi
import com.example.notemark.note.presentation.model.toNoteUi
import com.example.notemark.note.presentation.preview.PreviewModels
import java.util.UUID

@Composable
fun NoteCard(
    note: NoteUi,
    isTablet: Boolean,
    modifier: Modifier = Modifier
) {
    val content = if (isTablet && note.content.length > 250)
        note.content.take(250) + "..."
    else if (!isTablet && note.content.length > 150)
        note.content.take(150) + "..."
    else
        note.content

    Surface(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .shadow(8.dp, RoundedCornerShape(12.dp))
    ) {
        Column(
            verticalArrangement = spacedBy(8.dp),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                .padding(16.dp),
        ) {
            Text(
                text = note.createdAtFormatted,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NoteCardPreview() {
    NoteMarkTheme {
        val note = PreviewModels.note.copy(
            id = UUID.randomUUID().toString()
        )
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            NoteCard(
                note = note.toNoteUi(),
                isTablet = false
            )
        }

    }
}