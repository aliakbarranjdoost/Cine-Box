package dev.aliakbar.tmdbunofficial.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import dev.aliakbar.tmdbunofficial.data.toExternal
import dev.aliakbar.tmdbunofficial.util.PAGE_SIZE

private var TAG = TopMoviesPagingSource::class.java.simpleName

class TopMoviesPagingSource(
    private val networkDataSource: TMDBApiService,
    private val basePosterUrl: String,
    private val baseBackdropUrl: String,
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
            val response = networkDataSource.getTopRatedMovies(page = page)
            LoadResult.Page(
                data = response.results.mapIndexed()
                { index, networkPopularMovie ->
                    networkPopularMovie.toExternal(
                        basePosterUrl, baseBackdropUrl,
                        (response.page - 1) * PAGE_SIZE + index.inc()
                    )
                },
                prevKey = if (page == 1) null else page.dec(),
                nextKey = if (response.results.isEmpty()) null else page.inc()
            )
        }
        catch (e: Exception)
        {
            LoadResult.Error(e)
        }
    }
}