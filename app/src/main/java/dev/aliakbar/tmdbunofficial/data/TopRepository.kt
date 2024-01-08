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
) : ConfigurationRepository(networkDataSource, localDataSource)
{
    fun getTopRatedMovies() = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            TopMoviesPagingSource(
                networkDataSource,
                localDataSource,
                basePosterUrl,
                baseBackdropUrl
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
                basePosterUrl,
                baseBackdropUrl
            )
        }
    ).flow

    suspend fun addTrendToBookmark(trend: Trend)
    {
        localDataSource.bookmarkDao().insert(trend.toBookmark().toLocal())
    }

    suspend fun removeFromBookmark(trend: Trend)
    {
        localDataSource.bookmarkDao().delete(trend.toBookmark().toLocal())
    }
}