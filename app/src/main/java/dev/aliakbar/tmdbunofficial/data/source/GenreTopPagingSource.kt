package dev.aliakbar.tmdbunofficial.data.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.aliakbar.tmdbunofficial.data.GenreTopRepository
import dev.aliakbar.tmdbunofficial.data.Trend

private var TAG = GenreTopPagingSource::class.java.simpleName

class GenreTopPagingSource(
    private val genreId: Int,
    private val repository: GenreTopRepository,

    ) : PagingSource<Int, Trend>()
{
    override fun getRefreshKey(state: PagingState<Int, Trend>): Int?
    {
        return state.anchorPosition?.let()
        { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Trend>
    {
        return try
        {
            val page = params.key ?: 1
            val response = repository.getGenreTopMovies(genreId, page)
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.isEmpty()) null else page.plus(1)
            )
        }
        catch (e: Exception)
        {
            Log.d(TAG, "error = ${e.message}")
            LoadResult.Error(e)
        }
    }
}