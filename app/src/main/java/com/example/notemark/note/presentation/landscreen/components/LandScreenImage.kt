package com.example.notemark.note.presentation.landscreen.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.notemark.R

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