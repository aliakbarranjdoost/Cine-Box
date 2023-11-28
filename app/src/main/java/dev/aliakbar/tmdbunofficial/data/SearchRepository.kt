package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import dev.aliakbar.tmdbunofficial.ui.search.SearchViewModel

private val TAG: String = SearchRepository::class.java.simpleName

class SearchRepository(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase
) : ConfigurationRepository(networkDataSource, localDataSource.configurationDao())
{
    suspend fun search(query: String, page: Int = 1): List<SearchResult>
    {
        return networkDataSource.multiSearch(query, page).results.toExternal(
            createBasePosterUrl(),
            createBaseBackdropUrl(),
            createBaseProfileUrl()
        )
    }
}