package com.example.notemark.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val colorScheme = lightColorScheme(
    background = background,
    primary = primary,
    onPrimary = onPrimary,
    surface = surface,
    onSurface = onSurface,
    onSurfaceVariant = onSurfaceVariant,
    error = error,
    surfaceContainerLowest = surfaceContainerLowest,

)

@Composable
fun NoteMarkTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}