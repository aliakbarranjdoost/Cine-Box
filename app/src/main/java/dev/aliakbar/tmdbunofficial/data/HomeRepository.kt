package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import dev.aliakbar.tmdbunofficial.util.MAX_TRAILERS_NUMBER
import javax.inject.Inject

private var TAG = HomeRepository::class.java.simpleName

class HomeRepository @Inject constructor(
    private val networkDataSource: TMDBApiService
) : ConfigurationRepository(networkDataSource)
{
    suspend fun getTodayTrendingMovies(): List<Trend>
    {
        return networkDataSource.getTodayTrendingMovies().results.toExternal(
            basePosterUrl = basePosterUrl,
            baseBackdropUrl = baseBackdropUrl
        )
    }

    suspend fun getThisWeekTrendingMovies(): List<Trend>
    {
        return networkDataSource.getThisWeekTrendingMovies().results.toExternal(
            basePosterUrl = basePosterUrl,
            baseBackdropUrl = baseBackdropUrl
        )
    }

    suspend fun getTodayTrendingSeries(): List<Trend>
    {
        return networkDataSource.getTodayTrendingSeries().results.toExternal(
            basePosterUrl = basePosterUrl,
            baseBackdropUrl = baseBackdropUrl
        )
    }

    suspend fun getThisWeekTrendingSeries(): List<Trend>
    {
        return networkDataSource.getThisWeekTrendingSeries().results.toExternal(
            basePosterUrl = basePosterUrl,
            baseBackdropUrl = baseBackdropUrl
        )
    }

    suspend fun getPopularMovies(): List<Trend>
    {
        return networkDataSource.getPopularMovies().results.toExternal(
            basePosterUrl = basePosterUrl,
            baseBackdropUrl = baseBackdropUrl
        )
    }

    suspend fun getPopularSeries(): List<Trend>
    {
        return networkDataSource.getPopularSeries().results.toExternal(
            basePosterUrl = basePosterUrl,
            baseBackdropUrl = baseBackdropUrl
        )
    }

    suspend fun getTodayTrendingMovieTrailers() : List<Trailer>
    {
        val todayTrendingMovieTrailers = mutableListOf<Trailer>()

        networkDataSource.getTodayTrendingMovies().results.forEach()
        {
            val movieTrailers = networkDataSource.getMovieVideos(it.id).results.toExternal()

            if (movieTrailers.isNotEmpty() && todayTrendingMovieTrailers.size <= MAX_TRAILERS_NUMBER)
            {
                val trailer = findOfficialTrailerFromYoutube(movieTrailers)
                if (trailer != null)
                {
                    todayTrendingMovieTrailers.add(
                        Trailer(
                            video = trailer,
                            trend = it.toExternal(basePosterUrl,baseBackdropUrl),
                        )
                    )
                }
            }
        }
        return todayTrendingMovieTrailers
    }

    private fun findOfficialTrailerFromYoutube(videos: List<Video>): Video?
    {
        videos.forEach()
        { video ->
            if (
                video.official &&
                video.site.lowercase() == VideoSite.YOUTUBE.name.lowercase() &&
                video.type.lowercase() == VideoType.TRAILER.name.lowercase()
                )
            {
                return video
            }
        }
        return null
    }
}