package com.mad.assignmentFive.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.mad.assignment1.R
import com.mad.assignmentFive.network.model.post.Post
import com.mad.assignmentFive.viewModel.UserViewModel


@Composable
fun HomeScreen(userViewModel: UserViewModel) {

    val posts = remember { userViewModel.posts }.collectAsLazyPagingItems()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Home") },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.Magenta,
                titleContentColor = Color.White
            )
        )
    }, bottomBar = {

    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            GetPosts(modifier = Modifier, posts)
        }
    }
}


@Composable
fun GetPosts(
    modifier: Modifier,
    posts: LazyPagingItems<Post>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(5.dp)
    ) {
        items(count = posts.itemCount) { index ->
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(5.dp),
               verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                posts[index]?.let { GetUserInfo(modifier = modifier, post = it) }
                posts[index]?.let { GetPostImage(modifier = modifier, post = it) }
                posts[index]?.let { GetPostDetails(modifier = modifier, post = it) }
                posts[index]?.let { GetPostCaption(modifier = modifier, post = it) }
                Spacer(modifier = modifier.size(8.dp))
            }
        }

        posts.apply {
            when (posts.loadState.refresh) {
                is LoadState.Loading -> {
                    item {
//                        LoadingIndicator(
//                            modifier = modifier
//                                .fillMaxWidth()
//                                .padding(16.dp)
//                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(50.dp),
                                strokeWidth = 2.dp
                            )
                        }
                    }
                }

                else -> {}
            }
        }
    }
}

@Composable
fun GetUserInfo(modifier: Modifier, post: Post) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(
            model = post.user.profilePicUrl,
            contentDescription = "profile_pic",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .padding(start = 10.dp)
                .size(40.dp)
                .clip(CircleShape)
                .border(BorderStroke(1.dp, Color.Black), CircleShape)
        )

        Spacer(modifier = modifier.size(10.dp))

        Text(
            text = post.user.username,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun GetPostImage(modifier: Modifier, post: Post) {
    SubcomposeAsyncImage(
        model = post.url,
        contentDescription = "post",
        loading = { LoadingIndicator(modifier = modifier) },
        contentScale = ContentScale.FillHeight,
        modifier = modifier
            .fillMaxSize()
    )
}


@Composable
fun GetPostDetails(modifier: Modifier, post: Post) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(id = R.drawable.heart),
            contentScale = ContentScale.Crop,
            contentDescription = "",
            modifier = modifier.size(20.dp)
        )
        Text(
            text = post.likesCount.toString(),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )

        Image(
            painterResource(id = R.drawable.chat),
            contentScale = ContentScale.Crop,
            contentDescription = "",
            modifier = modifier.size(30.dp)
        )
        Text(
            text = post.commentsCount.toString(),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )

    }
}

@Composable
fun GetPostCaption(modifier: Modifier, post: Post) {
    Text(
        text = post.caption,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        modifier = modifier.padding(start = 5.dp)
    )
}

@Composable
fun LoadingIndicator(modifier: Modifier) {
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(10.dp), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier = Modifier.size(50.dp), strokeWidth = 2.dp)
    }
}
