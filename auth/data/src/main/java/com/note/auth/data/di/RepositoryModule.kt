package com.note.auth.data.di

import com.note.auth.data.AuthRepositoryImpl
import com.note.auth.domain.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindsAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}