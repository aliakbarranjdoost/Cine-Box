package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import javax.inject.Inject

private val TAG: String = SearchRepository::class.java.simpleName

class SearchRepository @Inject constructor(
    private val networkDataSource: TMDBApiService
) : ConfigurationRepository(networkDataSource)
{
    suspend fun search(query: String, page: Int = 1): List<SearchResult>
    {
        return networkDataSource.multiSearch(query, page).results.toExternal(
            basePosterUrl = basePosterUrl,
            baseProfileUrl = baseProfileUrl
        )
    }
}