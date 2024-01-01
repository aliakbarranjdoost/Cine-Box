package dev.aliakbar.tmdbunofficial.data.source.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val LANGUAGE = "language"
const val ENGLISH = "en"

interface TMDBApiService
{
    @GET("configuration")
    suspend fun getConfiguration(): NetworkConfigurationResponse

    @GET("trending/movie/day?$LANGUAGE=$ENGLISH")
    suspend fun getTodayTrendingMovies(): NetworkResponse<List<NetworkTrendingMovie>>

    @GET("trending/movie/week?$LANGUAGE=$ENGLISH")
    suspend fun getThisWeekTrendingMovies(): NetworkResponse<List<NetworkTrendingMovie>>

    @GET("trending/tv/day?$LANGUAGE=$ENGLISH")
    suspend fun getTodayTrendingSeries(): NetworkResponse<List<NetworkTrendingSeries>>

    @GET("trending/tv/week?$LANGUAGE=$ENGLISH")
    suspend fun getThisWeekTrendingSeries(): NetworkResponse<List<NetworkTrendingSeries>>

    @GET("movie/popular?$LANGUAGE=$ENGLISH")
    suspend fun getPopularMovies(): NetworkResponse<List<NetworkPopularMovie>>

    @GET("tv/popular?$LANGUAGE=$ENGLISH")
    suspend fun getPopularSeries(): NetworkResponse<List<NetworkPopularSerial>>

    @GET("movie/{id}?append_to_response=credits,videos,images,recommendations&$LANGUAGE=$ENGLISH")
    suspend fun getMovieDetails(@Path("id") id : Int): NetworkMovieDetails

    @GET("tv/{id}?append_to_response=credits,videos,images,recommendations&$LANGUAGE=$ENGLISH")
    suspend fun getTvDetails(@Path("id") id : Int): NetworkTvDetails

    @GET("tv/{id}/season/{seasonNumber}?$LANGUAGE=$ENGLISH")
    suspend fun getSeasonDetails(@Path("id") id : Int, @Path("seasonNumber") seasonNumber : Int): NetworkSeasonDetails

    @GET("tv/{id}/season/{seasonNumber}/episode/{episodeNumber}?append_to_response=credits,videos,images&$LANGUAGE=$ENGLISH")
    suspend fun getEpisodeDetails(@Path("id") id : Int, @Path("seasonNumber") seasonNumber : Int, @Path("episodeNumber") episodeNumber: Int): NetworkEpisodeDetails

    @GET("movie/{id}/videos?$LANGUAGE=$ENGLISH")
    suspend fun getMovieVideos(@Path("id") id: Int): NetworkVideoResponse

    @GET("movie/top_rated?language=en-US")
    suspend fun getTopRatedMovies(@Query("page") page: Int): NetworkResponse<List<NetworkPopularMovie>>

    @GET("tv/top_rated?language=en-US")
    suspend fun getTopRatedSeries(@Query("page") page: Int): NetworkResponse<List<NetworkPopularSerial>>

    @GET("search/multi?query=lord&include_adult=true&language=en-US&page=1")
    suspend fun multiSearch(@Query("query") query: String, @Query("page") page: Int): NetworkResponse<List<NetworkMultiSearchResult>>
}