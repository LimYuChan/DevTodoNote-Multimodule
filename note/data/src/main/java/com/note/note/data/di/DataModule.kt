package com.note.note.data.di

import com.note.note.data.RepositoryEventFetcherImpl
import com.note.note.data.JsoupLinkMetaDataFetcher
import com.note.note.data.NoteRepositoryImpl
import com.note.note.domain.repository.LinkMetaDataFetcher
import com.note.note.domain.repository.NoteRepository
import com.note.note.domain.repository.RepositoryEventFetcher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {
    @Binds
    abstract fun bindsNoteRepository(impl: NoteRepositoryImpl): NoteRepository

    @Binds
    abstract fun bindsLinkMetaDataFetcher(impl: JsoupLinkMetaDataFetcher): LinkMetaDataFetcher

    @Binds
    abstract fun bindsRepositoryEventFetcher(impl: RepositoryEventFetcherImpl): RepositoryEventFetcher
}