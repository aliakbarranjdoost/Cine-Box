package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService

class PersonRepository(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase
) : ConfigurationRepository(networkDataSource, localDataSource)
{
    suspend fun getPerson(id: Int): PersonDetails
    {
        return networkDataSource.getPerson(id).toExternal(baseProfileUrl, baseBackdropUrl, basePosterUrl)
    }
}