package dev.aliakbar.tmdbunofficial.data.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import dev.aliakbar.tmdbunofficial.data.toExternal

private var TAG = TopMoviesPagingSource::class.java.simpleName

class TopMoviesPagingSource(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase,
    private val basePosterUrl: String,
    private val baseBackdropUrl: String,
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
            val response = networkDataSource.getTopRatedMovies(page = page)
            LoadResult.Page(
                data = response.results.mapIndexed()
                { index, networkPopularMovie ->
                    networkPopularMovie.toExternal(
                        basePosterUrl, baseBackdropUrl,
                        (response.page - 1) * 20 + index.inc(), isBookmark(networkPopularMovie.id),
                    )
                },
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1)
            )
        }
        catch (e: Exception)
        {
            LoadResult.Error(e)
        }
    }

    suspend fun isBookmark(id: Int): Boolean
    {
        return localDataSource.bookmarkDao().isBookmark(id)
    }
}