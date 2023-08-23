package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.LocalTrend
import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private var TAG = TrendingRepository::class.java.simpleName

class TrendingRepository(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase
) : ConfigurationRepository(networkDataSource, localDataSource.configurationDao())
{
    suspend fun getTodayTrendingMovies(): List<LocalTrend>
    {
        val baseUrl = imageConfiguration.secureBaseUrl + findBiggestPosterSize(imageConfiguration.posterSizes)
        return networkDataSource.getTodayTrendingMovies().results.toLocal(baseUrl)
    }

    suspend fun getThisWeekTrendingMovies(): List<LocalTrend>
    {
        val baseUrl = imageConfiguration.secureBaseUrl + findBiggestPosterSize(imageConfiguration.posterSizes)
        return networkDataSource.getThisWeekTrendingMovies().results.toLocal(baseUrl)
    }

    suspend fun getTodayTrendingSeries(): List<LocalTrend>
    {
        val baseUrl = imageConfiguration.secureBaseUrl + findBiggestPosterSize(imageConfiguration.posterSizes)
        return networkDataSource.getTodayTrendingSeries().results.toLocal(baseUrl)
    }

    suspend fun getThisWeekTrendingSeries(): List<LocalTrend>
    {
        val baseUrl = imageConfiguration.secureBaseUrl + findBiggestPosterSize(imageConfiguration.posterSizes)
        return networkDataSource.getThisWeekTrendingSeries().results.toLocal(baseUrl)
    }

    suspend fun getPopularMovies(): List<LocalTrend>
    {
        return networkDataSource.getPopularMovies().results.toLocal(createBaseImageUrl())
    }
    private fun findBiggestPosterSize(posterSizes: List<String>): String
    {
        return posterSizes[posterSizes.size - 2]
    }

    private fun createBaseImageUrl(): String
    {
        return imageConfiguration.secureBaseUrl + findBiggestPosterSize(imageConfiguration.posterSizes)
    }
}
