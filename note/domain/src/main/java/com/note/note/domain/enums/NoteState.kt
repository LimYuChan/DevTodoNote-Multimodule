package com.note.note.domain.enums

enum class NoteState(val code: Int) {
    TODO(code = 0),
    DONE(code = 1)
}

fun Int.toNoteState(): NoteState {
    return NoteState.entries.find { it.code == this } ?: NoteState.TODO
}