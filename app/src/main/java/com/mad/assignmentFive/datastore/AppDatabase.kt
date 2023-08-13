package com.mad.assignmentFive.datastore

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mad.assignmentFive.datastore.dao.UserDao
import com.mad.assignmentFive.network.model.user.User

@Database(version = 1, entities = [User::class])
abstract class AppDatabase : RoomDatabase() {

    abstract val userDao: UserDao
}