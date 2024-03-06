package dev.aliakbar.tmdbunofficial.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.aliakbar.tmdbunofficial.data.source.TopMoviesPagingSource
import dev.aliakbar.tmdbunofficial.data.source.TopSeriesPagingSource
import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import javax.inject.Inject

private var TAG = HomeRepository::class.java.simpleName

class TopRepository @Inject constructor(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase
) : ConfigurationRepository(networkDataSource, localDataSource.configurationDao(), localDataSource.bookmarkDao())
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
}