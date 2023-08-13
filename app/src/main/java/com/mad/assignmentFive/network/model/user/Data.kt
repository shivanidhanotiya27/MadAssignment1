package com.mad.assignmentFive.network.model.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val user: User
)