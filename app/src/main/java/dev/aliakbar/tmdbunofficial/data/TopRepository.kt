package dev.aliakbar.tmdbunofficial.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.aliakbar.tmdbunofficial.data.source.TopMoviesPagingSource
import dev.aliakbar.tmdbunofficial.data.source.TopSeriesPagingSource
import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService

private var TAG = HomeRepository::class.java.simpleName

class TopRepository(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase
) : ConfigurationRepository(networkDataSource, localDataSource.configurationDao())
{
    fun getTopRatedMovies() = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            TopMoviesPagingSource(
                networkDataSource,
                localDataSource,
                createBasePosterUrl(),
                createBaseBackdropUrl()
            )
        }
    ).flow

    fun getTopRatedSeries() = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            TopSeriesPagingSource(
                networkDataSource,
                localDataSource,
                createBasePosterUrl(),
                createBaseBackdropUrl()
            )
        }
    ).flow

    /*suspend fun getTopRatedMovies(): List<Trend>
    {
        return networkDataSource.getTopMovies().results.mapIndexed()
        { index, networkPopularMovie ->
            networkPopularMovie.toExternal(
                createBasePosterUrl(), createBaseBackdropUrl(),
                index.inc(), isBookmark(networkPopularMovie.id),
            )
        }
    }*/

    suspend fun isBookmark(id: Int): Boolean
    {
        return localDataSource.bookmarkDao().isBookmark(id)
    }
}