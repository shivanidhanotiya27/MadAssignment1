package com.mad.assignment4.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.mad.assignment4.network.retrofit.eventWrapper.ResponseState
import com.mad.assignment4.network.retrofit.model.post.PostItem
import com.mad.assignment4.viewModel.UserViewModel

@Composable
fun PostDetailsScreen(
    postId: Long,
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    val profileDetails by remember { userViewModel.postDetailsState }.collectAsState()
    val uriHandle = LocalUriHandler.current
    LaunchedEffect(key1 = Unit, block = {
        userViewModel.getPostDetails(postId)
    })

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Post Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, contentDescription = "Back"
                        )
                    }
                }, colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Cyan)
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(innerPadding)
        ) {
            when (profileDetails) {
                is ResponseState.Loading -> {
                    //LoadingIndicator(modifier = Modifier.fillMaxSize())
                }

                is ResponseState.Success -> {
                    profileDetails.data?.let {
                        ProfileDetailsUI(Modifier, it){ urlLink ->
                            uriHandle.openUri(urlLink)
                        }
                    }
                }

                is ResponseState.Failure -> {
                    OnFailureScreen(
                        modifier = Modifier.fillMaxWidth(),
                        errorMsg = "Something went wrong, Please try again"
                    )
                }

                else -> {}
            }

        }
    }

}

@Composable
fun ProfileDetailsUI(modifier: Modifier, data: PostItem, onImageUrlClick: (urlLink: String) -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(BorderStroke(2.dp, color = Color.Black), shape = RectangleShape)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    )
    {
        SubcomposeAsyncImage(
            model = data.download_url,
            contentDescription = "post_image",
            loading = { CircularProgressIndicator(color = Color.Black) },
            contentScale = ContentScale.FillHeight,
            modifier = modifier
                .fillMaxSize()
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(14.dp))
                .weight(1f)
        )

        ProfileDetailsInfo(modifier, data, onImageUrlClick)
    }
}

@Composable
fun ProfileDetailsInfo(modifier: Modifier, data: PostItem, onImageUrlClick: (urlLink: String) -> Unit) {
    Column(
        modifier = modifier
            .padding(5.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Author: ${data.author}",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )

        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Text(
                text = "Width ${data.width}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Text(
                text = "Height ${data.height}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }

        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "URL: ${data.download_url}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.Blue,
                modifier = Modifier.clickable { onImageUrlClick(data.download_url) }
            )
        }
    }
}
