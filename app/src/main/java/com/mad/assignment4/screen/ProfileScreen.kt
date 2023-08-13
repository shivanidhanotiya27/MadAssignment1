package com.mad.assignment4.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.mad.assignment4.network.retrofit.eventWrapper.ResponseState
import com.mad.assignment4.network.retrofit.model.post.PostItem
import com.mad.assignment4.network.retrofit.model.user.Address
import com.mad.assignment4.network.retrofit.model.user.Subscription
import com.mad.assignment4.network.retrofit.model.user.User
import com.mad.assignment4.viewModel.UserViewModel


@Composable
fun ProfileScreen(
    userViewModel: UserViewModel,
    onUserPostClick: (postId: Long) -> Unit
) {
    val userProfile by remember { userViewModel.userState }.collectAsState()
    val posts = remember { userViewModel.posts }.collectAsLazyPagingItems()
    val userName: MutableState<String> = remember { mutableStateOf("UserName") }


    LaunchedEffect(key1 = Unit, block = {
        userViewModel.getUser()
    })

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = userName.value) },
            colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Cyan))
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            when (userProfile) {
                is ResponseState.Loading -> {
                    LoadingIndicator(modifier = Modifier.fillMaxSize())
                }

                is ResponseState.Success -> {
                    userProfile.data?.let {
                       LaunchedEffect(userProfile) {
                           userName.value = it.username
                       }
                        UserProfileScreen(
                            modifier = Modifier,
                            user = it
                        )
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

            UserPosts(
                modifier = Modifier,
                posts = posts
            ) {
                onUserPostClick(it.id.toLong())
            }
        }
    }

}

@Composable
fun UserPosts(
    modifier: Modifier,
    posts: LazyPagingItems<PostItem>,
    onPostItemClick: (post: PostItem) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 100.dp),
        contentPadding = PaddingValues(5.dp)
    ) {
        items(count = posts.itemCount) { index ->
            UserPostItem(
                modifier = Modifier
                    .size(140.dp, 150.dp)
                    .fillMaxWidth()
                    .padding(5.dp, 5.dp),
                post = posts[index]!!
            ) {
                onPostItemClick(posts[index]!!)
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
fun UserPostItem(modifier: Modifier, post: PostItem, onPostItemClick: (post: PostItem) -> Unit) {
    Card(modifier = modifier
        .clickable { onPostItemClick(post) }) {
        SubcomposeAsyncImage(
            model = post.download_url,
            contentDescription = "user_posts",
            loading = { CircularProgressIndicator(color = Color.Black) },
            contentScale = ContentScale.FillHeight,
            modifier = modifier
                .fillMaxSize()
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(8.dp))
        )
    }

}

@Composable
fun UserProfileScreen(modifier: Modifier, user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        UserProfileInfo(modifier, user = user)
        UserAddress(modifier, userAddress = user.address)
        UserSubscriptionPlan(modifier, userSubscription = user.subscription)
    }
}

@Composable
fun UserProfileInfo(modifier: Modifier, user: User) {
    Row() {
        SubcomposeAsyncImage(
            model = user.avatar,
            loading = {
                CircularProgressIndicator(modifier = modifier.align(Alignment.Center),
                        color = Color.Black)
            },
            contentDescription = "user Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 10.dp)
                .size(80.dp)
                .clip(CircleShape)
                .border(BorderStroke(1.dp, Color.Gray), CircleShape),
        )
        Spacer(modifier = modifier.size(20.dp))

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "${user.first_name} ${user.last_name}",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp
            )
            Text(
                text = user.employment.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Text(
                text = user.email,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun UserAddress(modifier: Modifier, userAddress: Address) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, Color.Black, Shapes(ShapeDefaults.ExtraLarge).extraLarge)
            .padding(15.dp)

    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            UserAddressItemRow(modifier = modifier, userAddress.street_name, userAddress.street_address)
            UserAddressItemRow(modifier = modifier, userAddress.city, userAddress.state)
            UserAddressItemRow(modifier = modifier, userAddress.country, userAddress.zip_code)
        }
    }
}

@Composable
fun UserAddressItemRow(
    modifier: Modifier,
    userAddressItemOne: String,
    userAddressItemSecond: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = userAddressItemOne, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(text = userAddressItemSecond, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun UserSubscriptionPlan(modifier: Modifier, userSubscription: Subscription) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = userSubscription.plan, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
        Text(text = userSubscription.status, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Divider(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 20.dp),
            color = Color.Black,
            thickness = 3.dp
        )

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

@Composable
fun LoadingIndicator(modifier: Modifier) {
    Column(modifier = modifier) {
        CircularProgressIndicator(color = Color.Black)
    }
}