package com.example.my_webapp.ui.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navController: NavController) {

    val items = listOf(
        NavRoutes.Home,
        NavRoutes.MyCities,
        NavRoutes.Settings
    )

    NavigationBar {

        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { screen ->

            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(NavRoutes.Home.route)
                        launchSingleTop = true
                    }
                },
                icon = {},
                label = { Text(screen.route) }
            )
        }
    }
}