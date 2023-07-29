package com.mad.assignment3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mad.assignment3.ui.screens.UserDetailsUI
import com.mad.assignment3.ui.screens.UserListUI
import com.mad.assignment3.ui.screens.WelcomeScreen
import com.mad.assignment3.ui.viewModel.UserViewModel

@Composable
fun AppNavigation(navController: NavHostController, userViewModel: UserViewModel) {
    val userState = userViewModel.userStatus.collectAsState(false)
    val route = if (userState.value) AppRoute.UserListScreen.route else AppRoute.WelcomeScreen.route

    NavHost(
        navController = navController,
        startDestination = route
    ) {
        composable(route = AppRoute.WelcomeScreen.route) {
            WelcomeScreen() {
                userViewModel.addUsers(5)
            }
        }

        composable(route = AppRoute.UserListScreen.route) {
            val users = userViewModel.getUserList().collectAsState(initial = emptyList())
            UserListUI(users = users.value, onUserListItemClick = {
                navController.navigate(AppRoute.UserDetailsScreen.route + "/${it.userId}")
            }, onAddUserClick = {userViewModel.addUser() })
        }

        composable(
            route = AppRoute.UserDetailsScreen.route + "/{userId}",
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.LongType
                }
            )
        ) {
            val userId = it.arguments?.getLong("userId") ?: return@composable
            val user = userViewModel.getUserById(userId).collectAsState(initial = null)
            user.value?.let { it1 ->
                UserDetailsUI(user = it1) { deleteUser ->
                    userViewModel.deleteUser(user = deleteUser)
                    navController.popBackStack()
                }
            }
        }
    }
}