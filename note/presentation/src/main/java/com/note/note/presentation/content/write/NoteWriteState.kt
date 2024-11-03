package com.note.note.presentation.content.write

import com.note.note.domain.model.NoteContent
import com.note.note.domain.model.NoteImage
import com.note.note.presentation.content.model.NoteLinkUi
import com.note.note.presentation.content.write.util.WriteMode
import kotlinx.serialization.Serializable

@Serializable
data class NoteWriteState(
    val writeMode: WriteMode = WriteMode.CREATE,
    val isLoading: Boolean = false,
    val content: NoteContent = NoteContent(),
    val images: List<NoteImage> = emptyList(),
    val links: List<NoteLinkUi> = emptyList()
): java.io.Serializable