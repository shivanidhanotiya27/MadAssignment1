package com.mad.assignmentFive.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mad.assignmentFive.datastore.AppDataStore
import com.mad.assignmentFive.datastore.AppDatabase
import com.mad.assignmentFive.network.model.user.User
import com.mad.assignmentFive.network.service.ApiEndPointService
import com.mad.assignmentFive.pagination.PostsPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiEndPointService: ApiEndPointService,
    private val appDataStore: AppDataStore,
    private val appDatabase: AppDatabase
) {

    suspend fun getUser(
        onSuccess: (User) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val response = apiEndPointService.getUser()
        if (response.isSuccessful) {
            response.body()?.data?.user?.let { onSuccess(it) }
        } else {
            val errorMsg = response.errorBody()?.string()
            onFailure(errorMsg.toString())
        }
    }

    fun getUserStatus(): Flow<Boolean> {
        return appDataStore.getUserStatus()
    }

    suspend fun addUserToDB(user: User): Long {
        val rawId = appDatabase.userDao.addUser(user)
        rawId.let {
            appDataStore.addUserStatus(true)
        }
        return rawId
    }

    fun getPosts() = Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 30)) {
        PostsPagingSource(apiEndPointService)
    }.flow

    fun getUserFromDB(): Flow<User> {
        return appDatabase.userDao.getUser()
    }

}