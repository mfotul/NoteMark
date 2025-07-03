package com.example.notemark.note.presentation.landscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notemark.R
import com.example.notemark.core.presentation.designsystem.buttons.NoteButton
import com.example.notemark.core.presentation.designsystem.buttons.NoteOutlinedButton
import com.example.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import com.example.notemark.note.presentation.landscreen.components.LandScreenContent
import com.example.notemark.note.presentation.landscreen.components.LandScreenImage

@Composable
fun LandScreen(
    isPortrait: Boolean,
    isTablet: Boolean,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        if (isPortrait) {
            Box(
                modifier = modifier
                    .padding(innerPadding)
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .fillMaxSize()
            ) {
                LandScreenImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = if (isTablet) 70.dp else 0.dp)
                ) {
                    LandScreenContent(
                        onRegisterClick = onRegisterClick,
                        onLoginClick = onLoginClick,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                            )
                    )
                }
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(0.9f)
                ) {
                    LandScreenImage(
                        modifier = Modifier
                            .graphicsLayer(
                                scaleY = 1.2f,
                                scaleX = 1.2f,
                                translationY = 100f,
                            )
                    )
                }
                LandScreenContent(
                    onRegisterClick = onRegisterClick,
                    onLoginClick = onLoginClick,
                    modifier = Modifier
                        .weight(1.1f)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainerLowest,
                            shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
                        )
                        .windowInsetsPadding(WindowInsets.navigationBars)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LandScreenPhonePreview() {
    NoteMarkTheme {
        LandScreen(isPortrait = true, isTablet = false, {}, {})
    }
}

@Composable
@Preview(showBackground = true, widthDp = 900, heightDp = 400)
fun LandScreenPhoneLandscapePreview() {
    NoteMarkTheme {
        LandScreen(isPortrait = false, isTablet = false, {}, {})
    }
}

@Composable
@Preview(showBackground = true, widthDp = 1000, heightDp = 1500)
fun LandScreenTabletPreview() {
    NoteMarkTheme {
        LandScreen(isPortrait = true, isTablet = true, {}, {})
    }
}
