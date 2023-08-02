package com.mad.assignment4.network.retrofit.service

import com.mad.assignment4.network.retrofit.model.user.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiEndPointServiceForUser {

    @GET("users")
    suspend fun getUser(): Response<User>

}