package com.note.note.data.di

import com.note.note.data.RepositoryEventService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesRepositoryEventService(retrofit: Retrofit): RepositoryEventService {
        return retrofit.create(RepositoryEventService::class.java)
    }
}