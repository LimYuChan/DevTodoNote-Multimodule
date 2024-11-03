package com.note.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.note.core.database.entity.NoteImageEntity

@Dao
interface NoteImageDao {

    @Upsert
    suspend fun upsertImages(noteImages: List<NoteImageEntity>): List<Long>

    @Query("SELECT * FROM note_image WHERE content_id = :contentId")
    suspend fun getImagesByContentId(contentId: Long): List<NoteImageEntity>

    @Delete
    suspend fun deleteImages(images: List<NoteImageEntity>): Int

    @Query("DELETE FROM note_image WHERE content_id = :contentId")
    suspend fun deleteImagesByContentId(contentId: Long): Int
}