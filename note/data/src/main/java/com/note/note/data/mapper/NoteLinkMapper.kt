package com.note.note.data.mapper

import com.note.core.database.entity.NoteLinkEntity
import com.note.note.domain.model.NoteLink

fun NoteLinkEntity.toNoteLink(): NoteLink {
    return NoteLink(
        id = id,
        contentId = content_id,
        link = url,
        title = title ?: "",
        description = description ?: "",
        image = image ?: ""
    )
}

fun NoteLink.toNoteLinkEntity(): NoteLinkEntity {
    return NoteLinkEntity(
        id = id,
        content_id = contentId,
        url = link,
        title = title,
        description = description,
        image = image
    )

}