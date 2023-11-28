package dev.aliakbar.tmdbunofficial.data.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.aliakbar.tmdbunofficial.data.MultiSearchResult
import dev.aliakbar.tmdbunofficial.data.SearchRepository
import dev.aliakbar.tmdbunofficial.data.SearchResult
import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import dev.aliakbar.tmdbunofficial.data.toExternal

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
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResult>
    {
        return try
        {
            val page = params.key ?: 1
            val response = repository.search( query = query, page = page)
            Log.d(TAG, "load: $response")
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.isEmpty()) null else page.plus(1)
            )
        }
        catch (e: Exception)
        {
            Log.d(TAG, "load: error")
            LoadResult.Error(e)
        }
    }
}