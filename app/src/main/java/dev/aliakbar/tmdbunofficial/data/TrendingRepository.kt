package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.network.NetworkTrending
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkTrendingMovie
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService

interface TrendingRepository
{
    suspend fun getTrendingTodayMovies(): NetworkTrending<NetworkTrendingMovie>
}

class NetworkTrendingRepository(
    private val tmdbApiService: TMDBApiService
): TrendingRepository
{
    override suspend fun getTrendingTodayMovies(): NetworkTrending<NetworkTrendingMovie>
    {
        return tmdbApiService.getTodayTrendingMovies()
    }
}