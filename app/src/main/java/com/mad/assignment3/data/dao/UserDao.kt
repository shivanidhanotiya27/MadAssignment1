package com.mad.assignment3.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mad.assignment3.ui.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUsers(users: List<User>) : List<Long>

    @Insert
    fun addUser(user: User): Long

    @Query("Select * from User")
    fun getUserList(): Flow<List<User>>

    @Query("Select * from User where userId = :userId")
    fun getUser(userId: Long): Flow<User>

    @Delete
    fun deleteUser(user: User):Int
}