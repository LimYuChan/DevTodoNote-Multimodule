package com.note.note.presentation.content.write.util

import com.esafirm.imagepicker.model.Image
import com.note.note.domain.model.NoteImage

fun Image.toNoteImage(): NoteImage {
    return NoteImage(
        fileId = id,
        fileName = name,
        filePath = path
    )
}