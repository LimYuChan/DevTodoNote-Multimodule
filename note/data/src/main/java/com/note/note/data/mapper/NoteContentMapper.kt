package com.note.note.data.mapper

import com.note.core.database.entity.NoteContentEntity
import com.note.note.domain.enums.toBranchState
import com.note.note.domain.enums.toNoteState
import com.note.note.domain.model.NoteContent

fun NoteContentEntity.toNoteContent(): NoteContent {
    return NoteContent(
        id = id,
        content = content,
        createdAt = created_at,
        updatedAt = updated_at,
        repositoryId = repository_id,
        branchName = branch_name ?: "",
        branchState = branch_state.toBranchState(),
        state = state.toNoteState()
    )
}

fun NoteContent.toNoteContentEntity(): NoteContentEntity {
    return NoteContentEntity(
        id = id,
        content = content,
        created_at = createdAt,
        updated_at = updatedAt,
        repository_id = repositoryId,
        branch_name = branchName,
        branch_state = branchState.stateCode,
        state = state.code
    )
}