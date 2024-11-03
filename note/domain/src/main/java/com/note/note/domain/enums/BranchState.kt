package com.note.note.domain.enums

enum class BranchState(val stateCode: Int) {
    NONE(0),
    COMMIT(1),
    MERGE(2)
}

fun Int.toBranchState(): BranchState {
    return BranchState.entries.find { it.stateCode == this } ?: BranchState.NONE
}