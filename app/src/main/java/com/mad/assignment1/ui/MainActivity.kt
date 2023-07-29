package com.mad.assignment1.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mad.assignment1.ui.ui.User
import kotlin.random.Random


private val userGenerator = generateSequence {
    User(
        userId = Random.nextLong(10000000,99999999),
        userName = generateAlphaNumericString(6),
        fullName = generateAlphabeticString(1,20),
        email = getEmailString()
    )
}

val users = userGenerator.take(5)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(users.toList())
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(users: List<User>) {
    Scaffold(topBar = { AppBar() }, modifier = Modifier
        .fillMaxSize()
        ) {
            UserListItems(users)
        }
}

@Composable
fun AppBar() {
    TopAppBar(
        title = {
            Text(text = "User Directory",
            fontFamily = FontFamily.SansSerif)
        }, colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Blue,
            titleContentColor = Color.White
        )
    )
}

@Composable
fun UserListItems(userList: List<User>){
    val context = LocalContext.current
    LazyColumn(modifier = Modifier.padding(top = 70.dp)) {
        itemsIndexed(userList){ userIndex, user ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 10.dp)
                    .border(
                        BorderStroke(2.dp, Color.Black),
                        RoundedCornerShape(50.dp)
                    )
                    .padding(10.dp)
                    .clickable {
                        Toast
                            .makeText(
                                context,
                                "You've clicked on ${user.userName}",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    })
            {
                UserListItemsContent(user = user, userIndex)
            }
        }
    }
}

@Composable
fun UserListItemsContent(user: User, userIndex: Int){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        UserItemContent(user,
            Modifier
                .weight(1f)
                .padding(5.dp))
        UserItemNumber(userIndex)
    }

}

@Composable
fun UserItemContent(user: User, modifier: Modifier) {
    Column(
          modifier = modifier)
        {
            Text(text = "UserId: ${user.userId}")
            Text(text = "Username: ${user.userName}")
        }

}

@Composable
fun UserItemNumber(userIndex: Int) {
    Column() {
        Box(modifier = Modifier
            .size(45.dp)
            .clip(CircleShape)
            .border(BorderStroke(2.dp, Color.Black), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "${userIndex + 1}")
        }
    }


   /*Text(modifier = Modifier
       .padding(16.dp)
       .drawBehind {
           drawCircle(
               color = Color.Black,
               radius = 20.dp.toPx(),
               style = Stroke(2.dp.toPx())
           )
       },
       text = "${userIndex + 1}")*/
}

fun generateAlphaNumericString(length: Int): String {
    val alphaNumericString = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..length).map {
        alphaNumericString[Random.nextInt(alphaNumericString.size)]
    }.joinToString("")
}

fun generateAlphabeticString(minLength: Int, maxLength: Int): String {
    val alphabeticString = ('a'..'z') + ('A'..'Z')
    val length = Random.nextInt(minLength, maxLength + 1)
    return (1..length)
        .map {
            alphabeticString[Random.nextInt(alphabeticString.size)]
        }.joinToString("")

}

fun getEmailString(): String {
    val username = generateAlphaNumericString(8)
    return "$username@gmail.com"
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen(users.toList())
}