package com.mad.assignment3.ui.navigation

sealed class AppRoute(val route: String) {
    object WelcomeScreen: AppRoute(route = "welcome_screen")
    object UserListScreen: AppRoute(route = "user_list_screen")
    object UserDetailsScreen: AppRoute(route = "user_details_screen")
}
