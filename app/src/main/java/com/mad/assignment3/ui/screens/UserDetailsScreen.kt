package com.mad.assignment3.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mad.assignment3.ui.User

@Composable
fun UserDetailsUI(user: User, onDeleteUserClick: (user: User) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.padding(top = 60.dp)
    ) {
        Text(text = "User Details Screen",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .border(2.dp, Color.Black, Shapes(ShapeDefaults.ExtraLarge).extraLarge)
        ) {
            Column(verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(20.dp)) {
                Text(text = "UserId : ${user.userId}",
                fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text(text = "UserName : ${user.userName}",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text(text = "FullName : ${user.fullName}",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text(text = "Email : ${user.email}",
                    fontWeight = FontWeight.Bold
                )

            }
        }

        Box() {
            Button(modifier = Modifier
                .wrapContentSize()
                .padding(10.dp)
                .clip(RectangleShape),
                onClick = { onDeleteUserClick(user) }) {
                Text(text = "Delete User", modifier = Modifier.padding(10.dp),
                    fontSize = 15.sp, color = Color.White, fontWeight = FontWeight.Bold
                )
            }
        }
    }

}