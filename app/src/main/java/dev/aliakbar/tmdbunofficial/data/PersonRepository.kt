package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import javax.inject.Inject

class PersonRepository @Inject constructor(
    private val networkDataSource: TMDBApiService,
    localDataSource: TmdbDatabase
) : ConfigurationRepository(networkDataSource, localDataSource.configurationDao())
{
    suspend fun getPerson(id: Int): PersonDetails
    {
        return networkDataSource.getPerson(id).toExternal(baseProfileUrl, baseBackdropUrl, basePosterUrl)
    }
}