package com.mad.assignmentFive.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mad.assignment4.screen.ProfileScreen
import com.mad.assignmentFive.screens.HomeScreen
import com.mad.assignmentFive.screens.LoginScreen
import com.mad.assignmentFive.screens.ProfileScreen
import com.mad.assignmentFive.viewModel.UserViewModel

@Composable
fun AppNavigation(
    modifier: Modifier,
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    val userState = userViewModel.localUserStatus.collectAsState(false)
    val route = if (userState.value) AppRoute.HomeScreen.route else AppRoute.LoginScreen.route

    NavHost(
        navController = navController,
        startDestination = route
    ) {
        composable(route = AppRoute.LoginScreen.route) {
            LoginScreen(userViewModel) {
                userViewModel.getUser()
            }
        }

        composable(route = AppRoute.HomeScreen.route) {
            HomeScreen(userViewModel)
        }

        composable(route = AppRoute.ProfileScreen.route) {
            val user = userViewModel.getUserFromDB().collectAsState(initial = null)
            user.value?.let { it1 ->
                ProfileScreen(it1) {
                    navController.navigate(AppRoute.PostDetailsScreen.route)
                }
            }
        }

        composable(route = AppRoute.PostDetailsScreen.route) {

        }
    }
}