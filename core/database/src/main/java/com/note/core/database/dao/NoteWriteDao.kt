package com.note.core.database.dao

import androidx.room.Dao
import androidx.room.Transaction
import com.note.core.common.Logg
import com.note.core.database.entity.NoteEntity
import com.note.core.database.entity.NoteImageEntity
import com.note.core.database.entity.NoteLinkEntity

@Dao
interface NoteWriteDao {

    @Transaction
    suspend fun upsertNote(
        contentDao: NoteContentDao,
        imageDao: NoteImageDao,
        linkDao: NoteLinkDao,
        noteEntity: NoteEntity
    ): Long {
        val now = System.currentTimeMillis()

        val noteCreatedAt = noteEntity.content.created_at

        // Content의 생성 및 갱신 시간 업데이트
        val updatedContent = noteEntity.content.copy(
            created_at = if(noteCreatedAt == 0L) now else noteCreatedAt,
            updated_at = now
        )
        val upsertContentResult = contentDao.upsertContent(updatedContent)

        val contentId = if(updatedContent.id != 0L) updatedContent.id else upsertContentResult

        val updatedImages = noteEntity.images.map { it.copy(content_id = contentId) }
        val updatedLinks = noteEntity.links.map { it.copy(content_id = contentId) }

        // 이미지와 링크 비교 후 필요한 데이터만 갱신
        updateImages(imageDao, contentId, updatedImages)
        updateLinks(linkDao, contentId, updatedLinks)

        return contentId
    }

    @Transaction
    suspend fun deleteNote(
        contentDao: NoteContentDao,
        imageDao: NoteImageDao,
        linkDao: NoteLinkDao,
        contentId: Long
    ): Long {
        contentDao.deleteContent(contentId)
        imageDao.deleteImagesByContentId(contentId)
        linkDao.deleteLinksByContentId(contentId)

        return contentId
    }

    private suspend fun updateImages(
        imageDao: NoteImageDao,
        contentId: Long,
        updatedImages: List<NoteImageEntity>
    ) {
        val currentImages = imageDao.getImagesByContentId(contentId)

        val imagesToDelete = currentImages.filterNot { it in updatedImages }
        val imagesToInsert = updatedImages.filterNot { it in currentImages }

        imageDao.deleteImages(imagesToDelete)
        imageDao.upsertImages(imagesToInsert)
    }

    private suspend fun updateLinks(
        linkDao: NoteLinkDao,
        contentId: Long,
        updatedLinks: List<NoteLinkEntity>
    ) {
        val currentLinks = linkDao.getLinksByContentId(contentId)

        val linksToDelete = currentLinks.filterNot { it in updatedLinks }
        val linksToInsert = updatedLinks.filterNot { it in currentLinks }

        linkDao.deleteLinks(linksToDelete)
        linkDao.upsertLinks(linksToInsert)
    }
}