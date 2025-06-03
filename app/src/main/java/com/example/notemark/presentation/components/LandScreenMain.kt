package com.example.notemark.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notemark.R
import com.example.notemark.ui.theme.NoteMarkTheme

@Composable
fun LandScreenMain(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.your_own_collection_of_notes),
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = stringResource(R.string.capture_your_thoughts_and_ideas),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        NoteButton(
            text = stringResource(R.string.get_started),
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        NoteOutlinedButton(
            text = stringResource(R.string.login),
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}


@Composable
@Preview
fun LandScreenMainPhonePreview() {
    NoteMarkTheme {
        LandScreenMain(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
        )
    }
}

@Composable
@Preview
fun LandScreenTabletPreview() {
    NoteMarkTheme {
        LandScreenMain(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
                )
        )
    }
}