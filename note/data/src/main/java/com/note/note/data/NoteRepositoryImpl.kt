package com.note.note.data

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.note.core.common.NoteDispatcher
import com.note.core.common.annotation.Dispatcher
import com.note.core.data.network.PagingConstant
import com.note.core.database.dao.NoteContentDao
import com.note.core.database.dao.NoteImageDao
import com.note.core.database.dao.NoteLinkDao
import com.note.core.database.dao.NoteReadDao
import com.note.core.database.dao.NoteWriteDao
import com.note.core.database.extension.safeDbCall
import com.note.core.domain.result.DataError
import com.note.core.domain.result.Result
import com.note.note.data.mapper.toNote
import com.note.note.data.mapper.toNoteEntity
import com.note.note.domain.enums.BranchState
import com.note.note.domain.enums.NoteState
import com.note.note.domain.model.Note
import com.note.note.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class NoteRepositoryImpl @Inject constructor(
    private val noteReadDao: NoteReadDao,
    private val noteWriteDao: NoteWriteDao,
    private val noteContentDao: NoteContentDao,
    private val noteImageDao: NoteImageDao,
    private val noteLinkDao: NoteLinkDao,
    @Dispatcher(NoteDispatcher.IO) private val ioDispatcher: CoroutineDispatcher
): NoteRepository {
    override fun getNotesByRepositoryId(repositoryId: Int): Flow<PagingData<Note>> {
        return Pager(
            config = PagingConstant.config,
            pagingSourceFactory = {  noteReadDao.getNotesByRepositoryId(repositoryId) }
        ).flow
            .map { pagingData -> pagingData.map { it.toNote() } }
            .flowOn(ioDispatcher)
    }

    override fun getNotesByState(repositoryId: Int, state: NoteState): Flow<PagingData<Note>> {
        return Pager(
            config = PagingConstant.config,
            pagingSourceFactory = {  noteReadDao.getNotesByStatus(repositoryId, state.code) }
        ).flow
            .map { pagingData -> pagingData.map { it.toNote() } }
            .flowOn(ioDispatcher)
    }

    override suspend fun getNote(contentId: Long): Result<Note?, DataError.Local> {
        return withContext(ioDispatcher) {
            safeDbCall { noteReadDao.getNote(contentId)?.toNote() }
        }
    }

    override suspend fun getLastContentId(): Result<Long?, DataError.Local> {
        return withContext(ioDispatcher) {
            safeDbCall { noteContentDao.getLastContentId() }
        }
    }

    override suspend fun upsertNote(note: Note): Result<Long, DataError.Local> {
        return withContext(ioDispatcher) {
            safeDbCall {
                noteWriteDao.upsertNote(
                    contentDao = noteContentDao,
                    imageDao = noteImageDao,
                    linkDao = noteLinkDao,
                    noteEntity = note.toNoteEntity()
                )
            }
        }
    }

    override suspend fun deleteNote(contentId: Long): Result<Long, DataError.Local> {
        return withContext(ioDispatcher) {
            safeDbCall {
                noteWriteDao.deleteNote(
                    contentDao = noteContentDao,
                    imageDao = noteImageDao,
                    linkDao = noteLinkDao,
                    contentId = contentId
                )
            }
        }
    }

    override suspend fun updateBranchState(
        branchState: BranchState,
        contentId: Long
    ): Result<Unit, DataError.Local> {
        return withContext(ioDispatcher) {
            safeDbCall { noteContentDao.updateBranchState(branchState.stateCode, contentId) }
        }
    }
}