package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.LocalTrend
import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService

private var TAG = HomeRepository::class.java.simpleName

class HomeRepository(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase
) : ConfigurationRepository(networkDataSource, localDataSource.configurationDao())
{
    suspend fun getTodayTrendingMovies(): List<Trend>
    {
        return networkDataSource.getTodayTrendingMovies().results.map()
        {
            it.toExternal(
                basePosterUrl,
                baseBackdropUrl,
                isBookmark(it.id)
            )
        }
        //return networkDataSource.getTodayTrendingMovies().results.toLocal(createBasePosterUrl(),createBaseBackdropUrl(),false)
    }

    suspend fun getThisWeekTrendingMovies(): List<LocalTrend>
    {
        return networkDataSource.getThisWeekTrendingMovies().results.toLocal(basePosterUrl,baseBackdropUrl,false)
    }

    suspend fun getTodayTrendingSeries(): List<LocalTrend>
    {
        return networkDataSource.getTodayTrendingSeries().results.toLocal(basePosterUrl,baseBackdropUrl,false)
    }

    suspend fun getThisWeekTrendingSeries(): List<LocalTrend>
    {
        return networkDataSource.getThisWeekTrendingSeries().results.toLocal(basePosterUrl,baseBackdropUrl,false)
    }

    suspend fun getPopularMovies(): List<LocalTrend>
    {
        return networkDataSource.getPopularMovies().results.toLocal(basePosterUrl,baseBackdropUrl,false)
    }

    suspend fun getPopularSeries(): List<LocalTrend>
    {
        return networkDataSource.getPopularSeries().results.toLocal(basePosterUrl,baseBackdropUrl,false)
    }

    suspend fun getTodayTrendingMovieTrailers() : List<Trailer>
    {
        val todayTrendingMovieTrailers = mutableListOf<Trailer>()

        networkDataSource.getTodayTrendingMovies().results.forEach()
        {
            val movieTrailers = networkDataSource.getMovieVideos(it.id).results.toExternal()

            if (movieTrailers.isNotEmpty())
            {
                val trailer = findOfficialTrailerFromYoutube(movieTrailers)
                if (trailer != null)
                {
                    todayTrendingMovieTrailers.add(
                        Trailer(
                            video = trailer,
                            trend = it.toExternal(basePosterUrl,baseBackdropUrl,false),
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

    suspend fun addTrendToBookmark(bookmark: Bookmark)
    {
        localDataSource.bookmarkDao().insert(bookmark.toLocal())
    }

    suspend fun isBookmark(id: Int): Boolean
    {
        return localDataSource.bookmarkDao().isBookmark(id)
    }
}
