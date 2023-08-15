package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService

private var TAG = TrendingRepository::class.java.simpleName

class TrendingRepository(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase
) : ConfigurationRepository(networkDataSource, localDataSource.configurationDao())
{
    suspend fun getTodayTrendingMovies(): List<Trend>
    {
        val baseUrl = imageConfiguration.secureBaseUrl + findBiggestPosterSize(imageConfiguration.posterSizes)
        return networkDataSource.getTodayTrendingMovies().results.toExternal(baseUrl)
    }

}

private fun findBiggestPosterSize(posterSizes: List<String>): String
{
    return posterSizes[posterSizes.size - 2]
}