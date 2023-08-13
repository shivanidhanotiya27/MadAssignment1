package com.mad.assignmentFive.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mad.assignmentFive.navigation.AppNavigation
import com.mad.assignmentFive.navigation.AppRoute
import com.mad.assignmentFive.navigation.bottomNavItems
import com.mad.assignmentFive.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = hiltViewModel()
    Scaffold(bottomBar = {
        BottomBarNavigation(
            destinations = bottomNavItems,
            onNavigateToDestination = { destination ->
                navController.navigate(destination.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            currentDestination = navController.currentBackStackEntryAsState().value?.destination
        )
    }) { innerPadding ->
        AppNavigation(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            userViewModel = userViewModel
        )
    }
}

@Composable
fun BottomBarNavigation(
    modifier: Modifier = Modifier,
    destinations: List<AppRoute>,
    onNavigateToDestination: (AppRoute) -> Unit,
    currentDestination: NavDestination?
) {
    NavigationBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination?.hierarchy?.any {
                it.route?.contains(destination.route, true) ?: false
            } ?: false

            val icon = if (selected) destination.selectedIcon else destination.unselectedIcon

            NavigationBarItem(selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    if (icon != null) {
                        Icon(imageVector = icon, contentDescription = destination.route)
                    }
                })
        }
    }
}
