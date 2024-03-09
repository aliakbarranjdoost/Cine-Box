package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val networkDataSource: TMDBApiService
) : ConfigurationRepository(networkDataSource)
{
    suspend fun getEpisodeDetails(tvId: Int, seasonNumber: Int, episodeNumber: Int): EpisodeDetails
    {
        return networkDataSource.getEpisodeDetails(tvId, seasonNumber, episodeNumber).toExternal(
            baseStillUrl, baseProfileUrl
        )
    }
}