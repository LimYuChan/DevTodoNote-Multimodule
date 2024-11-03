package com.note.note.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NoteImage(
    val id: Long = 0,
    val contentId: Long = 0,
    val fileId: Long,
    val fileName: String,
    val filePath: String
): java.io.Serializable
