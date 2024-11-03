package com.note.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.note.core.database.entity.NoteContentEntity

@Dao
interface NoteContentDao {
    @Upsert
    suspend fun upsertContent(content: NoteContentEntity): Long

    @Query("UPDATE note_content SET branch_state = :branchState WHERE id = :id")
    suspend fun updateBranchState(branchState: Int, id: Long)

    @Query("DELETE FROM note_content WHERE id = :id")
    suspend fun deleteContent(id: Long): Int

    @Query("SELECT id FROM note_content ORDER BY id DESC LIMIT 1")
    suspend fun getLastContentId(): Long?
}