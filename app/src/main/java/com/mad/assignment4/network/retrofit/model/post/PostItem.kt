package com.mad.assignment4.network.retrofit.model.post

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostItem(
    val author: String,
    val download_url: String,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)