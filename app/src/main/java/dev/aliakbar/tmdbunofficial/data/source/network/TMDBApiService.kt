package dev.aliakbar.tmdbunofficial.data.source.network

import retrofit2.http.GET
import retrofit2.http.Path

interface TMDBApiService
{
    @GET("configuration")
    suspend fun getConfiguration(): NetworkConfigurationResponse

    @GET("trending/movie/day")
    suspend fun getTodayTrendingMovies(): NetworkResponse<List<NetworkTrendingMovie>>

    @GET("trending/movie/week")
    suspend fun getThisWeekTrendingMovies(): NetworkResponse<List<NetworkTrendingMovie>>

    @GET("trending/tv/day")
    suspend fun getTodayTrendingSeries(): NetworkResponse<List<NetworkTrendingSeries>>

    @GET("trending/tv/week")
    suspend fun getThisWeekTrendingSeries(): NetworkResponse<List<NetworkTrendingSeries>>

    @GET("movie/popular")
    suspend fun getPopularMovies(): NetworkResponse<List<NetworkPopularMovie>>

    @GET("tv/popular")
    suspend fun getPopularSeries(): NetworkResponse<List<NetworkPopularSerial>>

    @GET("movie/{id}?append_to_response=credits,videos,images,recommendations")
    suspend fun getMovieDetails(@Path("id") id : Int): NetworkMovieDetails

    @GET("movie/{id}/videos")
    suspend fun getMovieVideos(@Path("id") id: Int): NetworkVideoResponse

    @GET("movie/top_rated?language=en-US&page=1")
    suspend fun getTopMovies(): NetworkResponse<List<NetworkTrendingMovie>>
}