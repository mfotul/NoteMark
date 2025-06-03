package com.example.notemark.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.notemark.R

val SpaceGrotesk = FontFamily(
    Font(
        resId = R.font.space_grotesk_medium,
        weight = FontWeight.Medium,
    ),
    Font(
        resId = R.font.space_grotesk_bold,
        weight = FontWeight.Bold,
    )
)

val Intern = FontFamily(
    Font(
        resId = R.font.inter_regular,
        weight = FontWeight.Normal,
    ),
    Font(
        resId = R.font.inter_medium,
        weight = FontWeight.Medium,
    )
)

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 36.sp,
        color = onSurface
    ),
    titleSmall = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        color = onSurface
    ),
    bodyLarge = TextStyle(
        fontFamily = Intern,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        color = onSurface
    ),
    bodyMedium = TextStyle(
        fontFamily = Intern,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        color = onSurface
    ),
    bodySmall = TextStyle(
        fontFamily = Intern,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        color = onSurface
    )
)

val Typography.titleXLarge: TextStyle
    get() = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 40.sp,
        color = onSurface
    )