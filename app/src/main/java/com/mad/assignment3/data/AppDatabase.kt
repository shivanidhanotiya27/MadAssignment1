package com.mad.assignment3.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mad.assignment3.ui.User
import com.mad.assignment3.data.dao.UserDao

//@Database(version = 1, entities = [User::class])
abstract class AppDatabase : RoomDatabase() {

    abstract val userDao: UserDao
}