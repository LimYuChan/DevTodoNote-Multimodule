package com.note.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "note_image",
    foreignKeys = [
        ForeignKey(
            entity = NoteContentEntity::class,
            parentColumns = ["id"],
            childColumns = ["content_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class NoteImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val content_id: Long,
    val file_id: Long,
    val file_name: String,
    val file_url: String
)
