package com.mad.assignmentFive.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mad.assignmentFive.network.retrofit.eventWrapper.ResponseState
import com.mad.assignmentFive.viewModel.UserViewModel

@Composable
fun LoginScreen(
    userViewModel: UserViewModel,
    onLoginButtonClick: () -> Unit
) {

    val userData by remember { userViewModel.userState }.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (userData) {
            is ResponseState.Loading -> {
                LoadingIndicator(modifier = Modifier.fillMaxWidth())
            }

            is ResponseState.Success -> {
               userData.data.let {
                   if (it != null) {
                       userViewModel.addUserToDB(it)
                   }
               }
            }

            is ResponseState.Failure -> {
               OnFailureScreen(modifier = Modifier,
                   errorMsg = "Something went wrong, Please try again"
               )
            }

            else -> {}
        }

        Text(
            text = "Hey There,",
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.size(20.dp))

        Button(
            onClick = onLoginButtonClick,
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Magenta
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "Login",
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}

@Composable
fun OnFailureScreen(modifier: Modifier, errorMsg: String) {
    Column(modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = errorMsg, color = Color.Red)
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
   // LoginScreen {}
}