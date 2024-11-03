package com.note.note.data.dto

data class RepositoryEventDto(
    val id: Long,
    val type: String,
    val payload: PayloadDto? = null
)
