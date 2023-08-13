package com.mad.assignmentFive.network.service

import com.mad.assignmentFive.network.model.post.Post
import com.mad.assignmentFive.network.model.post.PostModel
import com.mad.assignmentFive.network.model.request.DeletePostRequest
import com.mad.assignmentFive.network.model.user.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndPointService {

    @GET("users/random")
    suspend fun getUser(): Response<UserModel>

    @GET("posts/{postId}")
    suspend fun getPostByPostId(@Path("postId") pathId: Long): Response<Post>

    @GET("posts/users/{userId}")
    suspend fun getPostsByUser(
        @Path("userId") userId: Long
    ): Response<List<Post>>

    @GET("posts")
    suspend fun getPosts(
        @Query("page") pageNumber: Int,
        @Query("size") pageSize: Int
    ): Response<PostModel>


    @DELETE("posts/{postId}")
    suspend fun deleteUser(
        @Path("postId") postId: Long,
        @Body userId: DeletePostRequest)

}