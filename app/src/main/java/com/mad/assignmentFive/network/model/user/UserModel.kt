package com.mad.assignmentFive.network.model.user

import com.mad.assignmentFive.network.model.user.Data
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserModel(
    val `data`: Data,
    val status: Boolean
)