package com.mad.assignmentFive.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.mad.assignmentFive.network.model.user.User

@Composable
fun ProfileScreen(user: User, onPostClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = user.username) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        )
        {
            ProfileDetails(user = user, modifier = Modifier)
            Text(
                text = user.fullName,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(20.dp)
            )

            Text(
                text = user.biography,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}

@Composable
fun ProfileDetails(user: User, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        UserProfileInfo(modifier, user)
    }

}

@Composable
fun UserProfileInfo(modifier: Modifier, user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {
        SubcomposeAsyncImage(
            model = user.profilePicUrl,
            loading = { LoadingIndicator(modifier = modifier) },
            contentDescription = "user_profile",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(BorderStroke(1.dp, Color.Black), CircleShape)
        )
        
        UserProfileSubInfo(type = "Posts", userInfo = user.postsCount.toString())
        UserProfileSubInfo(type = "Followers", userInfo = user.followers.toString())
        UserProfileSubInfo(type = "Following", userInfo = user.following.toString())
    }
}

@Composable
fun UserProfileSubInfo(type: String, userInfo: String) {
    Column() {
        Text(
            text = userInfo,
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(20.dp)
        )

        Text(
            text = type,
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(20.dp)
        )
    }
}
