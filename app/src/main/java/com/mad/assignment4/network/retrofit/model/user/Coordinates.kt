package com.mad.assignment4.network.retrofit.model.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coordinates(
    val lat: Double,
    val lng: Double
)