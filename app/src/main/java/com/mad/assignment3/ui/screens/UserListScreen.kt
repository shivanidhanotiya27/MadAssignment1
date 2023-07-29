package com.mad.assignment3.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mad.assignment3.ui.User

@Composable
fun UserListUI(
    users: List<User>, onUserListItemClick: (user: User) -> Unit, onAddUserClick: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 65.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Welcome to User List Screen",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )

        LazyColumn(modifier = Modifier.padding(5.dp)
            .weight(1f)) {
            itemsIndexed(users) { userIndex, user ->
                UserListColumns(user, userIndex, onUserListItemClick)
            }
        }

        Box() {
            Button(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(10.dp)
                    .clip(RectangleShape),
                onClick = onAddUserClick
            ) {
                Text(
                    text = "Add User", modifier = Modifier.padding(10.dp),
                    fontSize = 15.sp, color = Color.White, fontWeight = FontWeight.Bold
                )
            }
        }

    }
}

@Composable
fun UserListColumns(user: User, userIndex: Int, onUserListItemClick: (user: User) -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp, 5.dp)
        .border(
            BorderStroke(2.dp, Color.Black), RoundedCornerShape(50.dp)
        )
        .clickable { onUserListItemClick(user) }
    ) {

        UserListItemsContent(user = user, userIndex)
    }
}

@Composable
fun UserListItemsContent(user: User, userIndex: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        UserItemContent(
            user,
            userIndex,
            Modifier
                .weight(1f)
                .padding(20.dp)
        )
    }
}

@Composable
fun UserItemContent(user: User, userIndex: Int, modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Row() {
            Text(text = "UserId: ${user.userId}")
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = "Username: ${user.userName}")
        }

        Spacer(modifier = Modifier.size(10.dp))

        Row() {
            Text(text = "FullName: \n${user.fullName}")
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = "Email: \n${user.email}")
        }

        Spacer(modifier = Modifier.size(10.dp))

        UserItemNumber(userIndex = userIndex)
    }

}


@Composable
fun UserItemNumber(userIndex: Int) {
    Box(
        modifier = Modifier
            .size(45.dp)
            .clip(CircleShape)
            .border(BorderStroke(2.dp, Color.Black), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "${userIndex + 1}")
    }
}

@Preview
@Composable
fun UserListScreenPreview() {
    //UserListUI()
}