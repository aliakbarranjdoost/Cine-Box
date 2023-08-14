package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.LocalConfigurationDao
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService

private var TAG = TrendingRepository::class.java.simpleName

class TrendingRepository(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: LocalConfigurationDao
) : ConfigurationRepository(networkDataSource, localDataSource)
{
    suspend fun getTodayTrendingMovies(): List<Trend>
    {
        val baseUrl = imageConfiguration.secureBaseUrl + imageConfiguration.posterSizes.max()
        return networkDataSource.getTodayTrendingMovies().results.toExternal(baseUrl)
    }
}