package com.mad.assignmentFive.network.model.post

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val biography: String,
    val email: String,
    val followers: Int,
    val following: Int,
    val fullName: String,
    val postsCount: Int,
    val profilePicUrl: String,
    val userId: Int,
    val username: String
)