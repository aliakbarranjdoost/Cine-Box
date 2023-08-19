package dev.aliakbar.tmdbunofficial.data.source.network

import retrofit2.http.GET

interface TMDBApiService
{
    @GET("configuration")
    suspend fun getConfiguration(): NetworkConfiguration

    @GET("trending/movie/day")
    suspend fun getTodayTrendingMovies(): NetworkResponse<List<NetworkTrendingMovie>>

    @GET("trending/movie/week")
    suspend fun getThisWeekTrendingMovies(): NetworkResponse<List<NetworkTrendingMovie>>

    @GET("trending/tv/day")
    suspend fun getTodayTrendingSeries(): NetworkResponse<List<NetworkTrendingSeries>>

    @GET("trending/tv/week")
    suspend fun getThisWeekTrendingSeries(): NetworkResponse<List<NetworkTrendingSeries>>
}