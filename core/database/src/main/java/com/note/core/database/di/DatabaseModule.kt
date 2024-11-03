package com.note.core.database.di

import android.content.Context
import androidx.room.Room
import com.note.core.database.NoteDatabase
import com.note.core.database.dao.NoteContentDao
import com.note.core.database.dao.NoteReadDao
import com.note.core.database.dao.NoteImageDao
import com.note.core.database.dao.NoteLinkDao
import com.note.core.database.dao.NoteWriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "note_database",
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesNoteReadDao(database: NoteDatabase): NoteReadDao {
        return database.noteReadDao()
    }

    @Provides
    fun providesNoteContentDao(database: NoteDatabase): NoteContentDao {
        return database.noteContentDao()
    }

    @Provides
    fun providesNoteImageDao(database: NoteDatabase): NoteImageDao {
        return database.noteImageDao()
    }

    @Provides
    fun providesNoteLinkDao(database: NoteDatabase): NoteLinkDao {
        return database.noteLinkDao()
    }

    @Provides
    fun providesNoteWriteDao(database: NoteDatabase): NoteWriteDao {
        return database.noteWriteDao()
    }
}