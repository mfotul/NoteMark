package com.example.notemark.core.presentation.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


val background = Color(0xFFDFEAFE)
val primary = Color(0xFF5977F7)
val colorPrimaryOpacity10 = Color(0x1A5977F7)
val onPrimary = Color(0xFFFFFFFF)
val colorOnPrimaryOpacity10 = Color(0x1AFFFFFF)
val surface = Color(0xFFEFEFF2)
val surfaceContainerLowest = Color(0xFFFFFFFF)
val onSurface = Color(0xFF1B1B1C)
val onSurfaceVariant = Color(0xFF535364)
val error = Color(0xFFE1294B)
val colorSurfaceOpacity12 = Color(0x1F1B1B1C)
val inverseSurface = Color(0xFFE91E63)



val ColorScheme.surfaceOpacity12: Color
    get() = colorSurfaceOpacity12

val ColorScheme.primaryOpacity10: Color
    get() = colorPrimaryOpacity10

val ColorScheme.onPrimaryOpacity10: Color
    get() = colorOnPrimaryOpacity10

val ColorScheme.buttonGradient: Brush
    get() = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF58A1F8),
            Color(0xFF5A4CF7)
        )
    )