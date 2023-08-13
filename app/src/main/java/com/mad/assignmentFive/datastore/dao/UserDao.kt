package com.mad.assignmentFive.datastore.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mad.assignmentFive.network.model.user.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User): Long

    @Query("Select * from User")
    fun getUser(): Flow<User>
}