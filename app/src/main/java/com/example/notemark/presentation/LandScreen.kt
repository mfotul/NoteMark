package com.example.notemark.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notemark.presentation.components.LandScreenImage
import com.example.notemark.presentation.components.LandScreenMain
import com.example.notemark.ui.theme.NoteMarkTheme

@Composable
fun LandScreen(
    isPortrait: Boolean,
    modifier: Modifier = Modifier
) {

    if (isPortrait) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.navigationBars)
        ) {
            LandScreenImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            )
            LandScreenMain(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainerLowest,
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    )
            )
        }
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.navigationBars)
                .background(MaterialTheme.colorScheme.background)
        ) {
            LandScreenImage(
                modifier = Modifier
                    .weight(1f)
            )
            LandScreenMain(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainerLowest,
                        shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
                    )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LandScreenPhonePreview() {
    NoteMarkTheme {
        LandScreen(isPortrait = true)
    }
}

@Composable
@Preview(showBackground = true, widthDp = 900, heightDp = 500)
fun LandScreenPhoneLandscapePreview() {
    NoteMarkTheme {
        LandScreen(isPortrait = false)
    }
}
