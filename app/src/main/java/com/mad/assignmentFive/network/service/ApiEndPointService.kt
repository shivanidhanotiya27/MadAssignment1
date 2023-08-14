package com.mad.assignmentFive.network.service

import com.mad.assignmentFive.network.model.post.Post
import com.mad.assignmentFive.network.model.post.PostModel
import com.mad.assignmentFive.network.model.request.DeletePostRequest
import com.mad.assignmentFive.network.model.user.UserModel
import com.mad.assignmentFive.network.model.userPost.UserPostModel
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndPointService {

    @GET("users/random")
    suspend fun getUser(): Response<UserModel>

    @GET("posts/{postId}")
    suspend fun getPostByPostId(@Path("postId") postId: Int): Response<UserPostModel>

    @GET("posts/users/{userId}")
    suspend fun getPostsByUser(
        @Path("userId") userId: Int,
        @Query("page") pageNumber: Int,
        @Query("size") pageSize: Int
    ): Response<PostModel>

    @GET("posts")
    suspend fun getPosts(
        @Query("page") pageNumber: Int,
        @Query("size") pageSize: Int
    ): Response<PostModel>


    @HTTP(method = "DELETE", path = "posts/{postId}", hasBody = true)
    suspend fun deletePost(
        @Path("postId") postId: Int,
        @Body userId: DeletePostRequest): Response<UserPostModel>
}