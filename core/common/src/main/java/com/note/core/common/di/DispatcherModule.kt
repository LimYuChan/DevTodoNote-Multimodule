package com.note.core.common.di

import com.note.core.common.NoteDispatcher
import com.note.core.common.annotation.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @Dispatcher(NoteDispatcher.Default)
    fun providesDefaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @Provides
    @Dispatcher(NoteDispatcher.Main)
    fun providesMainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    @Provides
    @Dispatcher(NoteDispatcher.IO)
    fun providesIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

}