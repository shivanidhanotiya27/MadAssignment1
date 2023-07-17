package com.mad.assignment1.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mad.assignment1.ui.ui.User
import kotlin.random.Random


private val userGenerator = generateSequence {
    User(
        userId = Random.nextLong(10000000,99999999),
        userName = generateAlphaNumericString(),
        fullName = generateAlphabeticString(),
        email = getEmailString() + "@gmail.com"
    )
}

val users = userGenerator.take(100)

class UserListActivity : ComponentActivity() {

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
    Scaffold(topBar = { AppBar() }) { it ->
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(top = 70.dp)) {
            UserListItems(users)
        }
    }
}

@Composable
fun AppBar() {
    TopAppBar(
        title = {
            Text(text = "Users",
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
    LazyColumn {
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
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        UserItemContent(user)
        UserItemNumber(userIndex)
    }

}

@Composable
fun UserItemContent(user: User) {
    Column(
          modifier = Modifier
              .padding(5.dp)
        ){
            Text(text = "UserId: ${user.userId}")
            Text(text = "Username: ${user.userName}")
        }

}

@Composable
fun UserItemNumber(userIndex: Int){
   Text(modifier = Modifier
       .padding(16.dp)
       .drawBehind {
           drawCircle(
               color = Color.Black,
               radius = 20.dp.toPx(),
               style = Stroke(2.dp.toPx())
           )
       },
       text = "${userIndex + 1}")
}

fun generateAlphaNumericString(): String {
    var randomAlphaNumericString = ""
    val alphaNumericString =
        ("ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz")
    for (i in 0..5) {
        val index = (alphaNumericString.length * Math.random()).toInt()
        randomAlphaNumericString += alphaNumericString[index]
    }
    return randomAlphaNumericString
}

fun generateAlphabeticString(): String {
    var randomAlphabeticString = ""
    val alphabeticString = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvxyz")
    for (i in 0..19) {
        val index = (alphabeticString.length * Math.random()).toInt()
        randomAlphabeticString += alphabeticString[index]
    }
    return randomAlphabeticString
}

fun getEmailString(): String? {
    val emailCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvxyz"
    var email = ""
    while (email.length < 12) {
        val index = (Math.random() * emailCHARS.length).toInt()
        email += emailCHARS[index]
    }
    return email
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    MainScreen(users.toList())
}