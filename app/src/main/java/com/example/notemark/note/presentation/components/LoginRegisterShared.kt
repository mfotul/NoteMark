package com.example.notemark.note.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.notemark.R


@Composable
fun LoginRegisterTop(
    title: String,
    isTablet: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = if (isTablet)
            Alignment.CenterHorizontally
        else
            Alignment.Start,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(R.string.capture_your_thoughts_and_ideas),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

