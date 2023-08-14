package com.mad.assignmentFive.network.model.userPost

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserPostModel(
    val `data`: Data,
    val status: Boolean
)