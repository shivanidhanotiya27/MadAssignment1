package com.mad.assignmentFive.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mad.assignmentFive.screens.HomeScreen
import com.mad.assignmentFive.screens.LoginScreen
import com.mad.assignmentFive.screens.ProfileScreen
import com.mad.assignmentFive.screens.postDetails.PostDetailsScreen
import com.mad.assignmentFive.screens.postDetails.PostDetailsViewModel
import com.mad.assignmentFive.viewModel.UserViewModel

@Composable
fun AppNavigation(
    modifier: Modifier,
    navController: NavHostController,
    userViewModel: UserViewModel,
    bottomBarState : MutableState<Boolean>
) {
    val userState = userViewModel.localUserStatus.collectAsState(false)
    val route = if (userState.value) AppRoute.HomeScreen.route else AppRoute.LoginScreen.route

    NavHost(
        navController = navController,
        startDestination = route
    ) {
        composable(route = AppRoute.LoginScreen.route) {
            bottomBarState.value = false
            LoginScreen(userViewModel) {
                userViewModel.getUser()
            }
        }

        composable(route = AppRoute.HomeScreen.route) {
            bottomBarState.value = true
            HomeScreen(userViewModel)
        }

        composable(route = AppRoute.ProfileScreen.route) {
            bottomBarState.value = true
            val user = userViewModel.getUserFromDB().collectAsState(initial = null)
            user.value?.let { it1 ->
                ProfileScreen(it1, userViewModel) {
                    navController.navigate(AppRoute.PostDetailsScreen.route + "/${it}")
                }
            }
        }

        composable(route = AppRoute.PostDetailsScreen.route + "/{postId}",
            arguments = listOf(
                navArgument("postId") {
                    type = NavType.IntType
                }
            )
        ) {
            bottomBarState.value = false
            val postId = it.arguments?.getInt("postId") ?: return@composable
            val postDetailsViewModel = hiltViewModel<PostDetailsViewModel>()
            PostDetailsScreen(postId, navController, userViewModel, postDetailsViewModel){ userId ->
                postDetailsViewModel.deletePost(postId, userId)
            }
        }
    }
}