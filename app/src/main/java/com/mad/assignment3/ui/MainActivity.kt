package com.mad.assignment3.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mad.assignment3.ui.navigation.AppNavigation
import com.mad.assignment3.ui.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(userViewModel)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(userViewModel: UserViewModel) {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    Scaffold(topBar = { AppBar() },
    modifier = Modifier.fillMaxSize()) {
       AppNavigation(navController = navController, userViewModel = userViewModel)
    }
}

@Composable
fun AppBar() {
    TopAppBar(
        title = {
            Text(text = "User Directory")
        }, colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Blue,
            titleContentColor = Color.White
        )
    )
}
