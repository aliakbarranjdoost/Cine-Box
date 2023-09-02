package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.LocalTrend
import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService

private var TAG = HomeRepository::class.java.simpleName

class HomeRepository(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase
) : ConfigurationRepository(networkDataSource, localDataSource.configurationDao())
{
    suspend fun getTodayTrendingMovies(): List<LocalTrend>
    {
        return networkDataSource.getTodayTrendingMovies().results.toLocal(createBaseImageUrl())
    }

    suspend fun getThisWeekTrendingMovies(): List<LocalTrend>
    {
        return networkDataSource.getThisWeekTrendingMovies().results.toLocal(createBaseImageUrl())
    }

    suspend fun getTodayTrendingSeries(): List<LocalTrend>
    {
        return networkDataSource.getTodayTrendingSeries().results.toLocal(createBaseImageUrl())
    }

    suspend fun getThisWeekTrendingSeries(): List<LocalTrend>
    {
        return networkDataSource.getThisWeekTrendingSeries().results.toLocal(createBaseImageUrl())
    }

    suspend fun getPopularMovies(): List<LocalTrend>
    {
        return networkDataSource.getPopularMovies().results.toLocal(createBaseImageUrl())
    }

    suspend fun getPopularSeries(): List<LocalTrend>
    {
        return networkDataSource.getPopularSeries().results.toLocal(createBaseImageUrl())
    }
}
