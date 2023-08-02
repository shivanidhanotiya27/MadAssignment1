package com.mad.assignment4.network.retrofit.service

import com.mad.assignment4.network.retrofit.model.post.PostItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

interface ApiEndPointServiceForPost {

    @GET("v2/list")
    suspend fun getPosts(
        @Query("page") pageNumber: Int,
        @Query("limit") pageSize: Int
    ): Response<List<PostItem>>


    @GET("id/{postId}/info")
    suspend fun getPostDetails(@Path("postId") postId: Long): Response<PostItem>

}
