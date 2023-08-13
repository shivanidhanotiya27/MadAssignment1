package com.mad.assignmentFive.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.mad.assignmentFive.datastore.AppDataStore
import com.mad.assignmentFive.datastore.AppDatabase
import com.mad.assignmentFive.datastore.dao.UserDao
import com.mad.assignmentFive.datastore.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context) : AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "appDatabasex"
        ).build()
    }

}

