package com.mad.assignmentFive.navigation

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class AppRoute(
    val route: String,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null
) {

    object LoginScreen : AppRoute(route = "login_screen")

    object HomeScreen : AppRoute(
        route = "home_screen",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )

    object ProfileScreen : AppRoute(
        route = "profile_screen",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    )

    object PostDetailsScreen : AppRoute(route = "post_details_screen")

   /* fun withArgs(vararg args: Any): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }*/
}

val bottomNavItems = listOf(
    AppRoute.HomeScreen,
    AppRoute.ProfileScreen
)