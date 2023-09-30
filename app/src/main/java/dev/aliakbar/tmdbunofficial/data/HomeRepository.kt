package dev.aliakbar.tmdbunofficial.data

import android.util.Log
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
        return networkDataSource.getTodayTrendingMovies().results.toLocal(createBasePosterUrl())
    }

    suspend fun getThisWeekTrendingMovies(): List<LocalTrend>
    {
        return networkDataSource.getThisWeekTrendingMovies().results.toLocal(createBasePosterUrl())
    }

    suspend fun getTodayTrendingSeries(): List<LocalTrend>
    {
        return networkDataSource.getTodayTrendingSeries().results.toLocal(createBasePosterUrl())
    }

    suspend fun getThisWeekTrendingSeries(): List<LocalTrend>
    {
        return networkDataSource.getThisWeekTrendingSeries().results.toLocal(createBasePosterUrl())
    }

    suspend fun getPopularMovies(): List<LocalTrend>
    {
        return networkDataSource.getPopularMovies().results.toLocal(createBasePosterUrl())
    }

    suspend fun getPopularSeries(): List<LocalTrend>
    {
        return networkDataSource.getPopularSeries().results.toLocal(createBasePosterUrl())
    }

    suspend fun getTodayTrendingMovieTrailers() : List<Pair<Video,Trend>>
    {
        val todayTrendingMovieTrailers = mutableListOf<Pair<Video,Trend>>()

        networkDataSource.getTodayTrendingMovies().results.forEach()
        {
            val movieTrailers = networkDataSource.getMovieVideos(it.id).results.toExternal()

            if (movieTrailers.isNotEmpty())
            {
                val trailer = findOfficialTrailerFromYoutube(movieTrailers)
                if (trailer != null)
                {
                    todayTrendingMovieTrailers.add( Pair(trailer,it.toExternal(createBasePosterUrl())) )
                }
            }
        }
        return todayTrendingMovieTrailers
    }

    private fun findOfficialTrailerFromYoutube(videos: List<Video>): Video?
    {
        videos.forEach()
        {
                video ->
            if (video.official && video.site == "YouTube" && video.type == "Trailer")
            {
                return video
            }
        }
        return null
    }
}
