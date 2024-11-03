package com.note.note.domain.repository

import com.note.core.domain.result.DataError
import com.note.core.domain.result.Result
import com.note.note.domain.model.NoteLink

interface LinkMetaDataFetcher {
    suspend fun fetch(link: String): Result<NoteLink, DataError.Parse>
}