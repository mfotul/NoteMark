package com.example.notemark.app.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationRoute {
    @Serializable
    data object Landing: NavigationRoute

    @Serializable
    data object Register: NavigationRoute

    @Serializable
    data object Login: NavigationRoute

    @Serializable
    data object List: NavigationRoute

    @Serializable
    data class Edit(val id: String, val fabClick: Boolean = false): NavigationRoute

    @Serializable
    data object Settings: NavigationRoute
}