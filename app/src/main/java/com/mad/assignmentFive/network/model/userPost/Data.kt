package com.mad.assignmentFive.network.model.userPost

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val post: UPost
)