package com.note.note.presentation.content.viewer

import com.note.note.domain.model.NoteContent
import com.note.note.domain.model.NoteImage
import com.note.note.presentation.content.model.NoteLinkUi
import kotlinx.serialization.Serializable

@Serializable
data class NoteViewerState(
    val isLoading: Boolean = false,
    val content: NoteContent = NoteContent(),
    val images: List<NoteImage> = emptyList(),
    val links: List<NoteLinkUi> = emptyList()
): java.io.Serializable
