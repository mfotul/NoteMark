package com.example.notemark.note.presentation.note.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notemark.R
import com.example.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import com.example.notemark.core.presentation.designsystem.theme.primaryOpacity10
import com.example.notemark.note.presentation.note.NoteAction

@Composable
fun NoteModeFloatingActionButton(
    onAction: (NoteAction) -> Unit,
    readerMode: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        IconButton(
            onClick = { onAction(NoteAction.OnPencilClick) },
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.pencil),
                contentDescription = stringResource(R.string.edit_mode),
            )
        }
        IconButton(
            onClick = { onAction(NoteAction.OnBookClick) },
            colors = if (readerMode) IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            ) else IconButtonDefaults.iconButtonColors(),
            modifier = if (readerMode) Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primaryOpacity10)
            else Modifier.padding(4.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.book_open),
                contentDescription = stringResource(R.string.view_mode)
            )
        }
    }
}

@Composable
@Preview(showBackground = false)
fun NoteModeFloatingActionButtonPreview() {
    NoteMarkTheme {
        NoteModeFloatingActionButton(
            onAction = {},
            readerMode = false
        )
    }
}