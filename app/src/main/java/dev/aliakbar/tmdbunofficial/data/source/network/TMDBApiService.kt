package dev.aliakbar.tmdbunofficial.data.source.network

import retrofit2.http.GET

interface TMDBApiService
{
    @GET("configuration")
    suspend fun getConfiguration(): NetworkConfiguration

    @GET("trending/movie/day")
    suspend fun <T> getToDayTrendingMovies(): NetworkTrending<T>
}