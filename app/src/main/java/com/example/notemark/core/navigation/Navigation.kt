package com.example.notemark.core.navigation

import android.content.res.Configuration
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowHeightSizeClass
import com.example.notemark.core.presentation.util.ObserveAsEvent
import com.example.notemark.note.presentation.landscreen.LandScreen
import com.example.notemark.note.presentation.login.LoginScreen
import com.example.notemark.note.presentation.login.LoginViewModel
import com.example.notemark.note.presentation.register.RegisterScreen
import com.example.notemark.note.presentation.register.RegisterViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

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
                onRegisterClick = {
                    navController.navigate(Register) {
                        popUpTo(Landing) {
                            inclusive = true
                        }
                    }
                },
                onLoginClick = {
                    navController.navigate(Login) {
                        popUpTo(Landing) {
                            inclusive = true
                        }
                    }
                },
                isPortrait = isPortrait,
                isTablet = isTablet
            )
        }
        composable<Register> {
            val viewModel: RegisterViewModel = koinViewModel()
            val uiState by viewModel.state.collectAsStateWithLifecycle()

            ObserveAsEvent(viewModel.isRegistered) {
                navController.navigate(Login) { popUpTo(Register) { inclusive = true } }
            }

            RegisterScreen(
                onLoginClick = {
                    navController.navigate(Login) { popUpTo(Register) { inclusive = true } }
                },
                isPortrait = isPortrait,
                isTablet = isTablet,
                uiState = uiState,
                onEvent = viewModel::onEvent
            )
        }
        composable<Login> {
            val viewModel: LoginViewModel = koinViewModel()
            val uiState by viewModel.state.collectAsStateWithLifecycle()

            ObserveAsEvent(viewModel.isLoggedIn) {
                navController.navigate(Empty) { popUpTo(Login) { inclusive = true } }
            }

            LoginScreen(
                onRegisterClick = {
                    navController.navigate(Register) { popUpTo(Login) { inclusive = true } }
                },
                isPortrait = isPortrait,
                isTablet = isTablet,
                email = uiState.email,
                password = uiState.password,
                isLoading = uiState.isLoading,
                onEvent = viewModel::onEvent
            )
        }
        composable<Empty> {

        }
    }

}

@Serializable
object Landing

@Serializable
object Register

@Serializable
object Login

@Serializable
object Empty