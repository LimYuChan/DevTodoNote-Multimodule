package com.note.note.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NoteLink(
    val id: Long = 0,
    val contentId: Long = 0,
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val link: String = ""
): java.io.Serializable {
    fun isValid(): Boolean {
        return title.isNotEmpty() || description.isNotEmpty() || image.isNotEmpty() || link.isNotEmpty()
    }
}
