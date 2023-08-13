package com.mad.assignmentFive.di

import com.mad.assignmentFive.datastore.AppDatabase
import com.mad.assignmentFive.network.service.ApiEndPointService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.hadiyarajesh.com/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiEndPointService(retrofit: Retrofit): ApiEndPointService =
        retrofit.create(ApiEndPointService::class.java)

}