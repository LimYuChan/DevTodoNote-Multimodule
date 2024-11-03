package com.note.home.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.note.core.data.network.PagingConstant.GITHUB_STARTING_PAGE_INDEX
import com.note.core.data.network.PagingConstant.PAGING_SIZE
import com.note.home.data.dto.toUserRepository
import com.note.home.data.service.RepositoryService
import com.note.home.domain.exception.UnauthorizedException
import com.note.home.domain.model.Repository
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

internal class RepositoryPagingSource(
    private val service: RepositoryService
): PagingSource<Int, Repository>(){

    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX

        return try {
            val response = service.getRepositories(position)

            val repos = response.map { it.toUserRepository() }
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                position + (params.loadSize / PAGING_SIZE)
            }

            LoadResult.Page(
                data = repos,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return if(exception.code() == 401) {
                LoadResult.Error(UnauthorizedException())
            }else{
                LoadResult.Error(exception)
            }
        } catch (throwable: Throwable) {
            return LoadResult.Error(throwable)
        }
    }
}