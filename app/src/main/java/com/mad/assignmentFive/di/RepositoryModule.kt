package com.mad.assignmentFive.di

import com.mad.assignmentFive.datastore.AppDataStore
import com.mad.assignmentFive.datastore.AppDatabase
import com.mad.assignmentFive.network.service.ApiEndPointService
import com.mad.assignmentFive.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        apiEndPointService: ApiEndPointService,
        appDatabase: AppDatabase,
        appDataStore: AppDataStore
    ): UserRepository {
        return UserRepository(
            apiEndPointService = apiEndPointService,
            appDataStore = appDataStore,
            appDatabase = appDatabase
        )
    }
}