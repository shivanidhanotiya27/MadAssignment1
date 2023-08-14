package com.mad.assignmentFive.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.mad.assignmentFive.network.model.userPost.UPost
import com.mad.assignmentFive.network.retrofit.eventWrapper.ResponseState
import com.mad.assignmentFive.viewModel.UserViewModel

@Composable
fun PostDetailsScreen(
    postId: Int,
    navController: NavHostController,
    userViewModel: UserViewModel,
    onDeleteIconClick: (userId: Int) -> Unit
) {
    val profileDetails by remember { userViewModel.postState }.collectAsState()
    val uriHandle = LocalUriHandler.current
    val deleteUser by remember { userViewModel.deletePostState }.collectAsState()
    val user = userViewModel.getUserFromDB().collectAsState(initial = null)

    LaunchedEffect(key1 = Unit, block = {
        userViewModel.getPostByPostId(postId)
    })

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Post Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        profileDetails.data?.user?.userId?.let { onDeleteIconClick(it) }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete, contentDescription = "delete Post",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            when (profileDetails) {
                is ResponseState.Loading -> {
                    LoadingIndicator(modifier = Modifier)
                }

                is ResponseState.Success -> {
                    profileDetails.data?.let { post ->
                        ProfileDetailsUI(Modifier, post) { urlLink ->
                            uriHandle.openUri(urlLink)
                        }
                    }
                }

                is ResponseState.Failure -> {
                    OnFailureScreen(
                        modifier = Modifier,
                        errorMsg = "Something went wrong, Please try again"
                    )
                }

                else -> {}
            }

            when (deleteUser) {
                is ResponseState.Loading -> {
                    LoadingIndicator(modifier = Modifier)
                }

                is ResponseState.Success -> {
                    deleteUser.data?.let { status ->
                        if (status) {
                            navController.popBackStack()
                        }
                    }
                }

                is ResponseState.Failure -> {
                    OnFailureScreen(
                        modifier = Modifier,
                        errorMsg = "Something went wrong, Please try again"
                    )
                }

                else -> {}
            }


        }
    }
}

@Composable
fun ProfileDetailsUI(modifier: Modifier, data: UPost, onImageUrlClick: (urlLink: String) -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    )
    {
        SubcomposeAsyncImage(
            model = data.url,
            contentDescription = "post_image",
            loading = { CircularProgressIndicator(color = Color.Black) },
            contentScale = ContentScale.FillHeight,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.CenterHorizontally)
                .weight(1f)
        )

        ProfileDetailsInfo(modifier, data)

        ProfileOtherDetails(
            modifier = modifier,
            infoType = "Created By",
            infoValue = data.user.fullName
        )
        ProfileOtherDetails(
            modifier = modifier,
            infoType = "Created At",
            infoValue = data.createdAt
        )
        ProfileOtherDetails(
            modifier = modifier.clickable { onImageUrlClick(data.url) },
            infoType = "Url:",
            infoValue = data.url
        )
    }
}

@Composable
fun ProfileDetailsInfo(
    modifier: Modifier,
    data: UPost
) {
    Column(
        modifier = modifier
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileLikesAndComments(
                modifier = modifier,
                infoType = "Likes",
                infoValue = data.likesCount
            )

            Spacer(modifier = modifier.size(50.dp))

            ProfileLikesAndComments(
                modifier = modifier,
                infoType = "Comments",
                infoValue = data.commentsCount
            )
        }
    }
}

@Composable
fun ProfileLikesAndComments(modifier: Modifier, infoType: String, infoValue: Int) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Text(
            text = infoValue.toString(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = infoType,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            modifier = modifier
        )
    }
}

@Composable
fun ProfileOtherDetails(modifier: Modifier, infoType: String, infoValue: String) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = infoType,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = modifier
        )
        Text(
            text = infoValue,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = modifier
        )
    }

}
