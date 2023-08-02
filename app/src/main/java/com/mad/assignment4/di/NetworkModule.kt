package com.mad.assignment4.di

import com.mad.assignment4.network.retrofit.service.ApiEndPointServiceForPost
import com.mad.assignment4.network.retrofit.service.ApiEndPointServiceForUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @Named("user-retrofit")
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://random-data-api.com/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    @Named("post-retrofit")
    fun providePostRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://picsum.photos/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiEndPointService(@Named("user-retrofit") retrofit: Retrofit): ApiEndPointServiceForUser =
        retrofit.create(ApiEndPointServiceForUser::class.java)

    @Singleton
    @Provides
    fun provideApiEndPointServiceForPost(@Named("post-retrofit") retrofit: Retrofit): ApiEndPointServiceForPost  =
        retrofit.create(ApiEndPointServiceForPost::class.java)


    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger.DEFAULT
        ).setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

}