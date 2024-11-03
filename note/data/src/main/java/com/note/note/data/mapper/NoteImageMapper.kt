package com.note.note.data.mapper

import com.note.core.database.entity.NoteImageEntity
import com.note.note.domain.model.NoteImage

fun NoteImageEntity.toNoteImage(): NoteImage {
    return NoteImage(
        id = id,
        contentId = content_id,
        fileId = file_id,
        fileName = file_name,
        filePath = file_url
    )
}

fun NoteImage.toNoteImageEntity(): NoteImageEntity {
    return NoteImageEntity(
        id = id,
        content_id = contentId,
        file_id = fileId,
        file_name = fileName,
        file_url = filePath
    )
}