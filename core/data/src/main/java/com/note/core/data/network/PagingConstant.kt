package com.note.core.data.network

import androidx.paging.PagingConfig

object PagingConstant {
    const val GITHUB_STARTING_PAGE_INDEX = 1
    const val PAGING_SIZE = 30

    val config = PagingConfig(
        pageSize = PAGING_SIZE,
        enablePlaceholders = false
    )
}