package com.note.core.common.di

import com.note.core.common.NoteDispatcher
import com.note.core.common.annotation.ApplicationScope
import com.note.core.common.annotation.Dispatcher
import com.note.core.common.annotation.MainScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineScopeModule {

    @Provides
    @Singleton
    @ApplicationScope
    fun provideApplicationScope(
        @Dispatcher(NoteDispatcher.Default) dispatcher: CoroutineDispatcher
    ): CoroutineScope {
        return CoroutineScope(SupervisorJob() + dispatcher)
    }

    @Provides
    @Singleton
    @MainScope
    fun provideMainScope(
        @Dispatcher(NoteDispatcher.Main) dispatcher: CoroutineDispatcher
    ): CoroutineScope {
        return CoroutineScope(dispatcher)
    }
}