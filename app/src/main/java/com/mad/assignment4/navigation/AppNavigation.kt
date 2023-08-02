package com.mad.assignment4.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mad.assignment4.screen.PostDetailsScreen
import com.mad.assignment4.screen.ProfileScreen
import com.mad.assignment4.viewModel.UserViewModel

@Composable
fun AppNavigation(navController: NavHostController, userViewModel: UserViewModel) {

    NavHost(
        navController = navController,
        startDestination = AppDestination.ProfileScreen.route
    ) {

        composable(route = AppDestination.ProfileScreen.route) {
            ProfileScreen(
                userViewModel = userViewModel, onUserPostClick = {
                    navController.navigate(AppDestination.PostDetailsScreen.route + "/${it}")
                }
            )
        }

        composable(route = AppDestination.PostDetailsScreen.route + "/{postId}",
            arguments = listOf(
                navArgument("postId") {
                    type = NavType.LongType
                }
            )
        ) {
            val postId = it.arguments?.getLong("postId") ?: return@composable
            PostDetailsScreen(postId, navController, userViewModel)
        }
    }

}