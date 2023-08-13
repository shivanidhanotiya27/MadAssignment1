package com.mad.assignmentFive.network.model.post

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostModel(
    val `data`: Data,
    val status: Boolean
)