package com.mad.assignment4.navigation

sealed class AppDestination(val route: String) {

    object ProfileScreen : AppDestination(route = "profile_screen")

    object PostDetailsScreen : AppDestination(route = "post_details_screen")
}