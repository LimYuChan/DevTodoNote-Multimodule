package com.note.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "note_link",
    foreignKeys = [
        ForeignKey(
            entity = NoteContentEntity::class,
            parentColumns = ["id"],
            childColumns = ["content_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class NoteLinkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val content_id: Long,
    val title: String,
    val description: String,
    val image: String,
    val url: String
)
