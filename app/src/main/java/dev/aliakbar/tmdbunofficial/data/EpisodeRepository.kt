package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val networkDataSource: TMDBApiService,
    localDataSource: TmdbDatabase
) : ConfigurationRepository(networkDataSource, localDataSource.configurationDao())
{
    suspend fun getEpisodeDetails(tvId: Int, seasonNumber: Int, episodeNumber: Int): EpisodeDetails
    {
        return networkDataSource.getEpisodeDetails(tvId, seasonNumber, episodeNumber).toExternal(
            baseStillUrl, baseProfileUrl
        )
    }
}