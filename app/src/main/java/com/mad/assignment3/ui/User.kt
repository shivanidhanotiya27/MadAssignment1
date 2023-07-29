package com.mad.assignment3.ui

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    var userId: Long,
    var userName: String,
    var fullName: String,
    var email: String
)

