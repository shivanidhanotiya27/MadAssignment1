package com.mad.assignment3.di

import android.content.Context
import androidx.room.Room
import com.mad.assignment3.data.AppDataStore
import com.mad.assignment3.data.AppDatabase
import com.mad.assignment3.ui.User
import com.mad.assignment3.ui.UserGenerator
import com.mad.assignment3.ui.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context,
            AppDatabase::class.java, "app_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): AppDataStore {
        return AppDataStore(context = context)
    }

    @Singleton
    @Provides
    fun provideUserRepository(userDatabase: AppDatabase, appDataStore: AppDataStore): UserRepository {
        return UserRepository(userDatabase,appDataStore)
    }

    @Singleton
    @Provides
    fun provideUserGenerator() : UserGenerator {
        return UserGenerator()
    }
}
