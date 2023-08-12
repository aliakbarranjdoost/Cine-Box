package dev.aliakbar.tmdbunofficial.data.source.network

import retrofit2.http.GET

interface TMDBApiService
{
    @GET("configuration")
    suspend fun getConfiguration(): NetworkConfiguration

    @GET("trending/movie/day")
    suspend fun getTodayTrendingMovies(): NetworkTrending<NetworkTrendingMovie>

    @GET("trending/movie/week")
    suspend fun getThisWeekTrendingMovies(): NetworkTrending<NetworkTrendingMovie>
}