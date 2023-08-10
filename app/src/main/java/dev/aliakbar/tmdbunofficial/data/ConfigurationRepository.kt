package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.network.NetworkConfiguration
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService

interface ConfigurationRepository
{
    suspend fun getConfiguration(): NetworkConfiguration
}

class NetworkConfigurationRepository(
    private val tmdbApiService: TMDBApiService
): ConfigurationRepository
{
    override suspend fun getConfiguration(): NetworkConfiguration
    {
        return tmdbApiService.getConfiguration()
    }
}