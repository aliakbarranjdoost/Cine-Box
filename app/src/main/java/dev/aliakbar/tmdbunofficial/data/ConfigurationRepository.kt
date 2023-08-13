package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.LocalConfigurationDao
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService

class ConfigurationRepository(
    private val remoteDataSource: TMDBApiService,
    private val localDataSource: LocalConfigurationDao
)
{

}