package com.example.notemark.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.notemark.app.navigation.NavigationRoot
import com.example.notemark.core.presentation.designsystem.theme.NoteMarkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        window.isNavigationBarContrastEnforced = false
        setContent {
            NoteMarkTheme {
                NavigationRoot()
            }
        }
    }
}
