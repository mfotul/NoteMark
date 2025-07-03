@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.notemark.note.presentation.settings.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import com.example.notemark.R
import com.example.notemark.core.presentation.designsystem.theme.NoteMarkTheme

@Composable
fun SettingsTopAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.back)
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.settings).uppercase(),
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = TextUnit(value = 0.1f, type = TextUnitType.Sp),
                modifier = Modifier
            )
        },
        modifier = modifier
    )
}

@Composable
@Preview
fun SettingsTopAppBarPreview() {
    NoteMarkTheme {
        SettingsTopAppBar(
            onBackClick = {}
        )
    }
}