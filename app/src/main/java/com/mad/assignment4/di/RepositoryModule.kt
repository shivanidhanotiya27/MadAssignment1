package com.mad.assignment4.di

import com.mad.assignment4.network.retrofit.service.ApiEndPointServiceForPost
import com.mad.assignment4.network.retrofit.service.ApiEndPointServiceForUser
import com.mad.assignment4.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    fun provideUserRepository(@Named("user-retrofit")apiEndPointService: ApiEndPointServiceForUser,
                              @Named("post-retrofit")apiEndPointServiceForPost: ApiEndPointServiceForPost
    ): UserRepository {
        return UserRepository(apiEndPointService, apiEndPointServiceForPost)
    }

}