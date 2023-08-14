package com.mad.assignmentFive.network.model.userPost

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UPost(
    val caption: String,
    val commentsCount: Int,
    val createdAt: String,
    val likesCount: Int,
    val postId: Int,
    val url: String,
    val user: User
)