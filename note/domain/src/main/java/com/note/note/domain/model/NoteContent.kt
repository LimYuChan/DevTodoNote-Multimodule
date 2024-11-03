package com.note.note.domain.model

import com.note.note.domain.enums.BranchState
import com.note.note.domain.enums.NoteState
import kotlinx.serialization.Serializable


@Serializable
data class NoteContent(
    val id: Long = 0,
    val content: String = "",
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
    val repositoryId: Int = 0,
    val branchName: String = "",
    val branchState: BranchState = BranchState.NONE,
    val state: NoteState = NoteState.TODO
): java.io.Serializable
