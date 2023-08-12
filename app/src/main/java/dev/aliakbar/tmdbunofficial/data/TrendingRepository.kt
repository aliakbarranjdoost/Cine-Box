package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.network.NetworkTrending
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkTrendingMovie
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkTrendingSeries
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService

interface TrendingRepository
{
    suspend fun getTrendingTodayMovies(): NetworkTrending<NetworkTrendingMovie>
    suspend fun getTrendingThisWeekMovies(): NetworkTrending<NetworkTrendingMovie>
    suspend fun getTrendingTodaySeries(): NetworkTrending<NetworkTrendingSeries>
    suspend fun getTrendingThisWeekSeries(): NetworkTrending<NetworkTrendingSeries>
}

class NetworkTrendingRepository(
    private val tmdbApiService: TMDBApiService
): TrendingRepository
{
    override suspend fun getTrendingTodayMovies(): NetworkTrending<NetworkTrendingMovie>
    {
        return tmdbApiService.getTodayTrendingMovies()
    }

    override suspend fun getTrendingThisWeekMovies(): NetworkTrending<NetworkTrendingMovie>
    {
        return tmdbApiService.getThisWeekTrendingMovies()
    }

    override suspend fun getTrendingTodaySeries(): NetworkTrending<NetworkTrendingSeries>
    {
        return tmdbApiService.getTodayTrendingSeries()
    }

    override suspend fun getTrendingThisWeekSeries(): NetworkTrending<NetworkTrendingSeries>
    {
        return tmdbApiService.getThisWeekTrendingSeries()
    }
}