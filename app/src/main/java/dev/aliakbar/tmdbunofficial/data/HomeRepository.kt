package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import javax.inject.Inject

private var TAG = HomeRepository::class.java.simpleName

class HomeRepository @Inject constructor(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase
) : ConfigurationRepository(networkDataSource, localDataSource.configurationDao(), localDataSource.bookmarkDao())
{
    suspend fun getTodayTrendingMovies(): List<Trend>
    {
        return networkDataSource.getTodayTrendingMovies().results.map()
        {
            it.toExternal(
                basePosterUrl = basePosterUrl,
                baseBackdropUrl = baseBackdropUrl
            )
        }
    }

    suspend fun getThisWeekTrendingMovies(): List<Trend>
    {
        return networkDataSource.getThisWeekTrendingMovies().results.mapIndexed()
        { index, networkTrendMovie ->
            networkTrendMovie.toExternal(basePosterUrl, baseBackdropUrl)
        }
    }

    suspend fun getTodayTrendingSeries(): List<Trend>
    {
        return networkDataSource.getTodayTrendingSeries().results.mapIndexed()
        { index, networkTrendSeries ->
            networkTrendSeries.toExternal(basePosterUrl, baseBackdropUrl)
        }
    }

    suspend fun getThisWeekTrendingSeries(): List<Trend>
    {
        return networkDataSource.getThisWeekTrendingSeries().results.mapIndexed()
        { index, networkTrendSeries ->
            networkTrendSeries.toExternal(basePosterUrl, baseBackdropUrl)
        }
    }

    suspend fun getPopularMovies(): List<Trend>
    {
        return networkDataSource.getPopularMovies().results.mapIndexed()
        { index, networkPopularMovie ->
            networkPopularMovie.toExternal(
                basePosterUrl, baseBackdropUrl,
                index.inc()
            )
        }
    }

    suspend fun getPopularSeries(): List<Trend>
    {
        return networkDataSource.getPopularSeries().results.mapIndexed()
        { index, networkPopularMovie ->
            networkPopularMovie.toExternal(
                basePosterUrl, baseBackdropUrl,
                index.inc()
            )
        }
    }

    suspend fun getTodayTrendingMovieTrailers() : List<Trailer>
    {
        val todayTrendingMovieTrailers = mutableListOf<Trailer>()

        networkDataSource.getTodayTrendingMovies().results.forEach()
        {
            val movieTrailers = networkDataSource.getMovieVideos(it.id).results.toExternal()

            if (movieTrailers.isNotEmpty() && todayTrendingMovieTrailers.size <= 10)
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