package com.example.notemark.note.presentation.note.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.notemark.R
import com.example.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import com.example.notemark.core.presentation.designsystem.theme.titleXSmall

@Composable
fun NoteViewTopAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = onBackClick
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.chevron_left),
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = stringResource(R.string.all_notes),
            style = MaterialTheme.typography.titleXSmall,
            modifier = modifier
        )
    }
}

@Composable
@Preview(showBackground = false)
fun NoteViewTopAppBarPreview() {
    NoteMarkTheme {
        NoteViewTopAppBar({})
    }
}