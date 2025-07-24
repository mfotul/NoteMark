package com.example.notemark.app.navigation

import android.content.res.Configuration
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowHeightSizeClass
import com.example.notemark.note.presentation.note.NoteScreenRoot
import com.example.notemark.note.presentation.landscreen.LandScreen
import com.example.notemark.note.presentation.list.ListScreenRoot
import com.example.notemark.note.presentation.login.LoginScreenRoot
import com.example.notemark.note.presentation.register.RegisterScreenRoot
import com.example.notemark.app.util.getInsetController
import com.example.notemark.note.presentation.settings.SettingsScreenRoot

@Composable
fun NavigationRoot() {

    val navController = rememberNavController()
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val windowsSize = currentWindowAdaptiveInfo()
    val windowSizeClass = windowsSize.windowSizeClass
    val isTablet = windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.EXPANDED
    var isDarkSystemIcons by remember { mutableStateOf(true) }

    NavHost(navController = navController, startDestination = NavigationRoute.Landing) {
        composable<NavigationRoute.Landing> {
            LandScreen(
                onRegisterClick = {
                    navController.navigate(NavigationRoute.Register) {
                        popUpTo(NavigationRoute.Landing) { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate(NavigationRoute.Login) {
                        popUpTo(NavigationRoute.Landing) { inclusive = true }
                    }
                },
                isPortrait = isPortrait,
                isTablet = isTablet
            )
        }
        composable<NavigationRoute.Register> {
            if (isDarkSystemIcons) {
                val insetsController = getInsetController()
                insetsController?.isAppearanceLightStatusBars = false
                isDarkSystemIcons = false
            }

            RegisterScreenRoot(
                isPortrait = isPortrait,
                isTablet = isTablet,
                onRegister = {
                    navController.navigate(NavigationRoute.Login) {
                        popUpTo(NavigationRoute.Register) { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate(NavigationRoute.Login) {
                        popUpTo(NavigationRoute.Register) { inclusive = true }
                    }
                }
            )

        }
        composable<NavigationRoute.Login> {
            if (isDarkSystemIcons) {
                val insetsController = getInsetController()
                insetsController?.isAppearanceLightStatusBars = false
                isDarkSystemIcons = false
            }

            LoginScreenRoot(
                isPortrait = isPortrait,
                isTablet = isTablet,
                onLogin = {
                    navController.navigate(NavigationRoute.List) {
                        popUpTo(NavigationRoute.Login) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(NavigationRoute.Register) {
                        popUpTo(NavigationRoute.Login) { inclusive = true }
                    }
                }
            )
        }
        composable<NavigationRoute.List> {
            if (!isDarkSystemIcons) {
                val insetsController = getInsetController()
                insetsController?.isAppearanceLightStatusBars = true
                isDarkSystemIcons = true
            }

            ListScreenRoot(
                isPortrait = isPortrait,
                isTablet = isTablet,
                onNoteClick = { id ->
                    navController.navigate(NavigationRoute.Edit(id))
                },
                onNotePosted = { id ->
                    navController.navigate(NavigationRoute.Edit(id, true))
                },
                onSettingsClick = {
                    navController.navigate(NavigationRoute.Settings)
                }
            )
        }
        composable<NavigationRoute.Edit> {
            NoteScreenRoot(
                isPortrait = isPortrait,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable<NavigationRoute.Settings> {
            SettingsScreenRoot(
                onBackClick = {
                    navController.popBackStack()
                },
                onLogOut = {
                    navController.navigate(NavigationRoute.Login) {
                        popUpTo(NavigationRoute.List) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}