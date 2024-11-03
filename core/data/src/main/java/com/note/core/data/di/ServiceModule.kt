package com.note.core.data.di

import com.note.core.data.user.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {

    @Provides
    @Singleton
    fun providesUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }
}