package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import dev.aliakbar.tmdbunofficial.data.source.sample.recommendations

class BookmarkRepository(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase
): ConfigurationRepository(networkDataSource, localDataSource.configurationDao())
{
    suspend fun getBookmarks() : List<Trend>
    {
        return recommendations
    }
}