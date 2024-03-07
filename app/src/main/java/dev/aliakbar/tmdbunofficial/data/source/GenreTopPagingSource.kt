package dev.aliakbar.tmdbunofficial.data.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.aliakbar.tmdbunofficial.data.GenreTopRepository
import dev.aliakbar.tmdbunofficial.data.Trend

private var TAG = GenreTopPagingSource::class.java.simpleName

class GenreTopPagingSource(
    private val genreId: Int,
    private val type: Boolean,
    private val repository: GenreTopRepository,

    ) : PagingSource<Int, Trend>()
{
    override fun getRefreshKey(state: PagingState<Int, Trend>): Int?
    {
        return state.anchorPosition?.let()
        { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.inc()
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.dec()
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Trend>
    {
        return try
        {
            val page = params.key ?: 1
            val response =
                if (type)
                    repository.getGenreTopMovies(genreId, page)
                else
                    repository.getGenreTopTvs(genreId, page)
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