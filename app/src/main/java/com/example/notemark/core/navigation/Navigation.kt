package com.example.notemark.core.navigation

import android.content.res.Configuration
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowHeightSizeClass
import com.example.notemark.note.presentation.landscreen.LandScreen
import com.example.notemark.note.presentation.login.LoginScreen
import com.example.notemark.note.presentation.register.RegisterScreen
import kotlinx.serialization.Serializable

@Composable
fun Navigation() {

    val navController = rememberNavController()
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val windowsSize = currentWindowAdaptiveInfo()
    val windowSizeClass = windowsSize.windowSizeClass
    val isTablet = windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.EXPANDED

    NavHost(navController = navController, startDestination = Landing) {
        composable<Landing> {
            LandScreen(
                onRegisterClick = { navController.navigate(Register) { popUpTo(Landing) { inclusive = true } } },
                onLoginClick = { navController.navigate(Login) { popUpTo(Landing) { inclusive = true } } },
                isPortrait = isPortrait,
                isTablet = isTablet
            )
        }
        composable<Register> {
            RegisterScreen(
                onLoginClick = {
                    navController.navigate(Login) { popUpTo(Register) { inclusive = true } }
                },
                isPortrait = isPortrait,
                isTablet = isTablet
            )
        }
        composable<Login> {
            LoginScreen(
                onRegisterClick = {
                    navController.navigate(Register) { popUpTo(Login) { inclusive = true } }
                },
                isPortrait = isPortrait,
                isTablet = isTablet
            )
        }
    }

}

@Serializable
object Landing

@Serializable
object Register

@Serializable
object Login