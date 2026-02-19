package com.example.my_webapp.ui.navigation

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object MyCities : NavRoutes("cities")
    object Settings : NavRoutes("settings")
}