package com.note.home.domain.model

data class Repository(
    val id: Int,
    val name: String,
    val private: Boolean,
    val htmlUrl: String,
    val description: String,
    val updatedAt: String,
    val language: String,
    val defaultBranch: String
)
