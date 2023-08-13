package com.mad.assignmentFive.network.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class User(
    val biography: String,
    val email: String,
    val followers: Int,
    val following: Int,
    val fullName: String,
    val postsCount: Int,
    val profilePicUrl: String,
    @PrimaryKey
    val userId: Int,
    val username: String
)