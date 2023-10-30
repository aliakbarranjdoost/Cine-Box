package dev.aliakbar.tmdbunofficial.data.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.aliakbar.tmdbunofficial.data.MultiSearchResult
import dev.aliakbar.tmdbunofficial.data.SearchResult
import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import dev.aliakbar.tmdbunofficial.data.toExternal

private var TAG = MultiSearchPagingSource::class.java.simpleName

class MultiSearchPagingSource(
    private val networkDataSource: TMDBApiService,
    private val basePosterUrl: String,
    private val baseBackdropUrl: String,
    private val baseProfileUrl: String,
) : PagingSource<Int, SearchResult>()
{
    public var searchQuery: String = ""
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
            Log.d(TAG, "load: $page")
            val response = networkDataSource.multiSearch(searchQuery, page = page)
            LoadResult.Page(
                data = response.results.toExternal(basePosterUrl, baseBackdropUrl, baseProfileUrl),
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1)
            )
        }
        catch (e: Exception)
        {
            LoadResult.Error(e)
        }
    }
}