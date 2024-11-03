package com.note.note.presentation.content.model

import com.note.note.domain.model.NoteLink
import kotlinx.serialization.Serializable


@Serializable
data class NoteLinkUi(
    val isLoading: Boolean = true,
    val linkIndex: Int = 0,
    val link: NoteLink
): java.io.Serializable
