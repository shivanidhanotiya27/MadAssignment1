package com.mad.assignmentFive.network.model.post

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val currentPage: Int,
    val hasNextPage: Boolean,
    val posts: List<Post>
)