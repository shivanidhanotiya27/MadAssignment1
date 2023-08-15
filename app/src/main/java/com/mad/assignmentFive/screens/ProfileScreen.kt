package com.mad.assignmentFive.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.mad.assignmentFive.network.model.post.Post
import com.mad.assignmentFive.network.model.user.User
import com.mad.assignmentFive.viewModel.UserViewModel

@Composable
fun ProfileScreen(user: User, userViewModel: UserViewModel, onPostClick: (postId: Int) -> Unit) {
    val userPosts =
        remember { userViewModel.getUserPosts(userId = user.userId) }.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = user.username) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Magenta,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
        {
            ProfileDetails(user = user, modifier = Modifier)
            Text(
                text = user.fullName,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 2.dp, start = 10.dp)

            )

            Text(
                text = user.biography,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 2.dp, start = 10.dp)
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                color = Color.Black,
                thickness = 3.dp
            )

            UserPosts(
                modifier = Modifier,
                posts = userPosts,
            ) {
                onPostClick(it.postId)
            }
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
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(30.dp)

    ) {
        SubcomposeAsyncImage(
            model = user.profilePicUrl,
            loading = { LoadingIndicator(modifier = modifier) },
            contentDescription = "user_profile",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(70.dp)
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
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = type,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
fun UserPosts(
    modifier: Modifier,
    posts: LazyPagingItems<Post>,
    onPostClick: (post: Post) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.padding(bottom = 30.dp),
        columns = GridCells.Adaptive(minSize = 120.dp),
        contentPadding = PaddingValues(5.dp)
    ) {
        items(count = posts.itemCount) { index ->
            UserPostItem(
                modifier = modifier
                    .fillMaxWidth()
                    .size(160.dp, 165.dp)
                    .padding(2.dp, 2.dp),
                post = posts[index]!!,
            ) {
                posts[index]?.let { it1 -> onPostClick(it1) }
            }
        }

        posts.apply {
            when (posts.loadState.refresh) {
                is LoadState.Loading -> {
                    item { LoadingIndicator(modifier = Modifier.fillMaxSize()) }
                }

                else -> {}
            }
        }
    }
}

@Composable
fun UserPostItem(
    modifier: Modifier,
    post: Post,
    onPostClick: (post: Post) -> Unit
) {
    Card(modifier = modifier
        .clickable { onPostClick(post) }) {
        SubcomposeAsyncImage(
            model = post.url,
            contentDescription = "user_posts",
            loading = { LoadingIndicator(modifier = modifier) },
            contentScale = ContentScale.FillHeight,
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .align(Alignment.CenterHorizontally)
        )
    }
}
