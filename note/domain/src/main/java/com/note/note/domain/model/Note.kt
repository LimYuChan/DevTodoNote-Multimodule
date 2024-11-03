package com.note.note.domain.model

data class Note(
    val content: NoteContent,
    val images: List<NoteImage>,
    val links: List<NoteLink>
)
