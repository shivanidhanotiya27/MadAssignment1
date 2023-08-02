package com.mad.assignment4.network.retrofit.model.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Employment(
    val key_skill: String,
    val title: String
)