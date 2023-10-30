package dev.aliakbar.tmdbunofficial.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.aliakbar.tmdbunofficial.data.source.MultiSearchPagingSource
import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkMultiSearchResult
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import kotlinx.coroutines.flow.Flow

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

    fun search(text: String): Flow<PagingData<MultiSearchResult>>
    {
        pagingSourceFactory.searchQuery = text
        return Pager(config = pagingConfig, pagingSourceFactory = { pagingSourceFactory }).flow
    }

    suspend fun search(text: String = "lord", page: Int = 1): List<MultiSearchResult>
    {
        return networkDataSource.multiSearch(text, page).results.toExternal(
            createBasePosterUrl(),
            createBaseBackdropUrl(),
            createBaseProfileUrl()
        )
    }
}