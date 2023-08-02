package com.mad.assignment4.network.retrofit.model.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Subscription(
    val payment_method: String,
    val plan: String,
    val status: String,
    val term: String
)