package com.note.note.data.mapper

import com.note.core.database.entity.NoteEntity
import com.note.note.domain.model.Note

fun NoteEntity.toNote(): Note {
    return Note(
        content = content.toNoteContent(),
        links = links.map { it.toNoteLink() },
        images = images.map { it.toNoteImage() }
    )
}

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        content = content.toNoteContentEntity(),
        links = links.map { it.toNoteLinkEntity() },
        images = images.map { it.toNoteImageEntity() }
    )
}