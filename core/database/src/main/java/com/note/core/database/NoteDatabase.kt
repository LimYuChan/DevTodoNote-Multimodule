package com.note.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.note.core.database.dao.NoteContentDao
import com.note.core.database.dao.NoteReadDao
import com.note.core.database.dao.NoteImageDao
import com.note.core.database.dao.NoteLinkDao
import com.note.core.database.dao.NoteWriteDao
import com.note.core.database.entity.NoteContentEntity
import com.note.core.database.entity.NoteImageEntity
import com.note.core.database.entity.NoteLinkEntity

@Database(
    entities = [
        NoteContentEntity::class,
        NoteImageEntity::class,
        NoteLinkEntity::class
    ],
    version = 2
)
abstract class NoteDatabase: RoomDatabase(){
    abstract fun noteReadDao(): NoteReadDao
    abstract fun noteContentDao(): NoteContentDao
    abstract fun noteImageDao(): NoteImageDao
    abstract fun noteLinkDao(): NoteLinkDao
    abstract fun noteWriteDao(): NoteWriteDao
}