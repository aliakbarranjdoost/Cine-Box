package dev.aliakbar.tmdbunofficial.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.aliakbar.tmdbunofficial.data.source.MultiSearchPagingSource
import dev.aliakbar.tmdbunofficial.data.source.TopMoviesPagingSource
import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService

class SearchRepository(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase
) : ConfigurationRepository(networkDataSource, localDataSource.configurationDao())
{
    private val pagingConfig = PagingConfig(pageSize = 20)
    private val pagingSourceFactory = MultiSearchPagingSource(
        networkDataSource,
        createBasePosterUrl(),
        createBaseBackdropUrl(),
        createBaseProfileUrl()
    )

    fun search(text: String)
    {
        pagingSourceFactory.searchQuery = text
        Pager(config = pagingConfig, pagingSourceFactory = { pagingSourceFactory }).flow
    }
}