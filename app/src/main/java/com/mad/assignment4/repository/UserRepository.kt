package com.mad.assignment4.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mad.assignment4.network.retrofit.model.post.PostItem
import com.mad.assignment4.network.retrofit.model.user.User
import com.mad.assignment4.network.retrofit.service.ApiEndPointServiceForUser
import com.mad.assignment4.network.retrofit.service.ApiEndPointServiceForPost
import com.mad.assignment4.pagination.PostsPagingSource
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiEndPointService: ApiEndPointServiceForUser,
    private val apiEndPointServiceForPost: ApiEndPointServiceForPost
) {
    suspend fun getUser(
        onSuccess: (User) -> Unit,
        onFailure: (String, Int) -> Unit
    ) {
        val response = apiEndPointService.getUser()
        if(response.isSuccessful) {
            onSuccess(response.body()!!)
        } else {
            val errorMsg = response.errorBody()?.string()
           onFailure(errorMsg.toString(), response.code())
        }
    }

     fun getPosts() = Pager(config = PagingConfig(pageSize = 20)) {
         PostsPagingSource(apiEndPointServiceForPost)
     }.flow

    suspend fun getPostDetails(
        postId: Long,
        onSuccess: (PostItem) -> Unit,
        onFailure: (String, Int) -> Unit
    ) {
        val response = apiEndPointServiceForPost.getPostDetails(postId = postId)
        if(response.isSuccessful) {
            onSuccess(response.body()!!)
            Log.i("UserReppo", "Success")
        } else {
            val errorMsg = response.errorBody()?.string()
            onFailure(errorMsg.toString(), response.code())
            Log.i("UserReppo", "Failure")
        }

    }
}