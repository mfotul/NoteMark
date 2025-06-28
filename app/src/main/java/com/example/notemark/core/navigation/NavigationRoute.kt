package com.example.notemark.core.navigation

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
    data class Edit(val id: String): NavigationRoute
}