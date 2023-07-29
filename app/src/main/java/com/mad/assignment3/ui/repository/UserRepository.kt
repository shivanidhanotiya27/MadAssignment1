package com.mad.assignment3.ui.repository

import com.mad.assignment3.ui.User
import com.mad.assignment3.data.AppDataStore
import com.mad.assignment3.data.AppDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val appDatabase: AppDatabase,
    private val appDataStore: AppDataStore
    )
{
    fun getUserStatus(): Flow<Boolean> {
        return appDataStore.getUserStatus()
    }

    suspend fun addUsers(users: List<User>): List<Long> {
        val rawID = appDatabase.userDao.addUsers(users)
        rawID.let {
            appDataStore.addUserStatus(true)
        }
        return rawID
    }

    fun getUserList() : Flow<List<User>> {
        return appDatabase.userDao.getUserList()
    }

    fun getUser(userId: Long): Flow<User> {
        return appDatabase.userDao.getUser(userId)
    }

    fun deleteUser(user: User): Int {
        return appDatabase.userDao.deleteUser(user)
    }
}