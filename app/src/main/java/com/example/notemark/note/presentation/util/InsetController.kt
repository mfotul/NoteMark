package com.example.notemark.note.presentation.util

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun getInsetController(): WindowInsetsControllerCompat? {
    val view = LocalView.current

    return remember(view) {
        (view.context as? Activity)
            ?.window
            ?.let { WindowCompat.getInsetsController(it, it.decorView) }
    }

}