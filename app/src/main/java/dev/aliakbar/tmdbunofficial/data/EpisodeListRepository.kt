package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService

class EpisodeListRepository(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase
) : ConfigurationRepository(networkDataSource, localDataSource.configurationDao())
{
    suspend fun getSeasonDetails(id: Int, seasonNumber: Int): SeasonDetails
    {
        return networkDataSource.getSeasonDetails(id, seasonNumber)
            .toExternal(basePosterUrl = basePosterUrl, baseStillUrl = baseStillUrl)
    }
}