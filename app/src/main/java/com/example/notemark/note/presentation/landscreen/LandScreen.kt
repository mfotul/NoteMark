package com.example.notemark.note.presentation.landscreen

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notemark.R
import com.example.notemark.note.presentation.components.NoteButton
import com.example.notemark.note.presentation.components.NoteOutlinedButton
import com.example.notemark.ui.theme.NoteMarkTheme

@Composable
fun LandScreen(
    isPortrait: Boolean,
    isTablet: Boolean,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isPortrait) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.navigationBars)
                .background(MaterialTheme.colorScheme.background)
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
                LandScreenMain(
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
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.navigationBars)
                .background(MaterialTheme.colorScheme.background)
        ) {
            LandScreenImage(
                modifier = Modifier
                    .weight(1f)
            )
            LandScreenMain(
                onRegisterClick = onRegisterClick,
                onLoginClick = onLoginClick,
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
fun LandScreenImage(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.image),
        contentDescription = stringResource(R.string.top_image),
        contentScale = ContentScale.FillWidth,
        modifier = modifier
    )
}

@Composable
fun LandScreenMain(
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
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
            onClick = onRegisterClick,
            isEnabled = true,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        NoteOutlinedButton(
            text = stringResource(R.string.login),
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
        )
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
@Preview(showBackground = true, widthDp = 900, heightDp = 500)
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
