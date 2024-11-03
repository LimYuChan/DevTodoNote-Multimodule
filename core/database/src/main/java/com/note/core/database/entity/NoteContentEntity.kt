package com.note.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_content")
data class NoteContentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val content: String,
    val created_at: Long,
    val updated_at: Long,
    val repository_id: Int,
    val branch_name: String? = null,
    val branch_state: Int = 0,
    val state: Int = 0
)
