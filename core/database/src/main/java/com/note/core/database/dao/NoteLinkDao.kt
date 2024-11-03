package com.note.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.note.core.database.entity.NoteImageEntity
import com.note.core.database.entity.NoteLinkEntity

@Dao
interface NoteLinkDao {

    @Upsert
    suspend fun upsertLinks(links: List<NoteLinkEntity>): List<Long>

    @Query("SELECT * FROM note_link WHERE content_id = :contentId")
    suspend fun getLinksByContentId(contentId: Long): List<NoteLinkEntity>

    @Delete
    suspend fun deleteLinks(links: List<NoteLinkEntity>): Int

    @Query("DELETE FROM note_link WHERE content_id = :contentId")
    suspend fun deleteLinksByContentId(contentId: Long): Int
}