package dev.aliakbar.tmdbunofficial.data.source.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val LANGUAGE = "language"
const val ENGLISH = "en"
const val INCLUDE_ADULTS = "include_adults"
const val INCLUDE_VIDEO = "include_video"
const val SORT_BY = "sort_by"
const val VOTE_COUNT = "vote_count"
const val MIN_VOTE_COUNT = 1000

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

    @GET("movie/top_rated?$LANGUAGE=$ENGLISH")
    suspend fun getTopRatedMovies(@Query("page") page: Int): NetworkResponse<List<NetworkPopularMovie>>

    @GET("tv/top_rated?$LANGUAGE=$ENGLISH")
    suspend fun getTopRatedSeries(@Query("page") page: Int): NetworkResponse<List<NetworkPopularSerial>>

    @GET("search/multi?include_adult=true&$LANGUAGE=$ENGLISH")
    suspend fun multiSearch(@Query("query") query: String, @Query("page") page: Int): NetworkResponse<List<NetworkMultiSearchResult>>

    @GET("discover/movie?$INCLUDE_ADULTS=false&$INCLUDE_VIDEO=false&$LANGUAGE=$ENGLISH&$SORT_BY=vote_average.desc&$VOTE_COUNT.gte=$MIN_VOTE_COUNT")
    suspend fun getTopRatedMoviesInGenre(@Query("with_genres") genreId: Int, @Query("page") page: Int): NetworkResponse<List<NetworkTrendingMovie>>
    @GET("discover/tv?$INCLUDE_ADULTS=false&$INCLUDE_VIDEO=false&$LANGUAGE=$ENGLISH&$SORT_BY=vote_average.desc&$VOTE_COUNT.gte=$MIN_VOTE_COUNT")
    suspend fun getTopRatedTvsInGenre(@Query("with_genres") genreId: Int, @Query("page") page: Int): NetworkResponse<List<NetworkTrendingSeries>>

    @GET("person/{id}?append_to_response=combined_credits,images&$LANGUAGE=$ENGLISH")
    suspend fun getPerson(@Path("id") id: Int): NetworkPersonDetails
}