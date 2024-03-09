package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import javax.inject.Inject

class SeasonDetailsRepository @Inject constructor(
    private val networkDataSource: TMDBApiService
) : ConfigurationRepository(networkDataSource)
{
    suspend fun getSeasonDetails(id: Int, seasonNumber: Int): SeasonDetails
    {
        return networkDataSource.getSeasonDetails(id, seasonNumber)
            .toExternal(basePosterUrl = basePosterUrl, baseStillUrl = baseStillUrl)
    }
}