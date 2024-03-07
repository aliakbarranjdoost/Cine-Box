package dev.aliakbar.tmdbunofficial.data.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.aliakbar.tmdbunofficial.data.SearchRepository
import dev.aliakbar.tmdbunofficial.data.SearchResult

private var TAG = MultiSearchPagingSource::class.java.simpleName

class MultiSearchPagingSource(
    private val query: String,
    private val repository: SearchRepository,

) : PagingSource<Int, SearchResult>()
{
    override fun getRefreshKey(state: PagingState<Int, SearchResult>): Int?
    {
        return state.anchorPosition?.let()
        { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.inc()
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.dec()
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResult>
    {
        return try
        {
            val page = params.key ?: 1
            val response = repository.search( query = query, page = page)
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page.dec(),
                nextKey = if (response.isEmpty()) null else page.inc()
            )
        }
        catch (e: Exception)
        {
            LoadResult.Error(e)
        }
    }
}