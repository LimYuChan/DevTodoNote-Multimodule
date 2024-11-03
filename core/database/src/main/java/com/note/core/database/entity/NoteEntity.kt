package com.note.core.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class NoteEntity(
    @Embedded
    val content: NoteContentEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "content_id"
    )
    val images: List<NoteImageEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "content_id"
    )
    val links: List<NoteLinkEntity>
)
