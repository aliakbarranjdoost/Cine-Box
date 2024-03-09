package dev.aliakbar.tmdbunofficial.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.aliakbar.tmdbunofficial.data.source.TopMoviesPagingSource
import dev.aliakbar.tmdbunofficial.data.source.TopSeriesPagingSource
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import dev.aliakbar.tmdbunofficial.util.PAGE_SIZE
import javax.inject.Inject

private var TAG = HomeRepository::class.java.simpleName

class TopRepository @Inject constructor(
    private val networkDataSource: TMDBApiService
) : ConfigurationRepository(networkDataSource)
{
    // TODO: change page size to const
    fun getTopRatedMovies() = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
        ),
        pagingSourceFactory = {
            TopMoviesPagingSource(
                networkDataSource,
                basePosterUrl,
                baseBackdropUrl
            )
        }
    ).flow

    fun getTopRatedSeries() = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
        ),
        pagingSourceFactory = {
            TopSeriesPagingSource(
                networkDataSource,
                basePosterUrl,
                baseBackdropUrl
            )
        }
    ).flow
}