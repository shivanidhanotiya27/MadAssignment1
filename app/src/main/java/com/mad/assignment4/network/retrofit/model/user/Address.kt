package com.mad.assignment4.network.retrofit.model.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Address(
    val city: String,
    val coordinates: Coordinates,
    val country: String,
    val state: String,
    val street_address: String,
    val street_name: String,
    val zip_code: String
)