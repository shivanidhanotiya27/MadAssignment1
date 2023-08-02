package com.mad.assignment4.network.retrofit.model.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val address: Address,
    val avatar: String,
    val email: String,
    val employment: Employment,
    val first_name: String,
    val gender: String,
    val id: Int,
    val last_name: String,
    val password: String,
    val phone_number: String,
    val subscription: Subscription,
    val uid: String,
    val username: String
)