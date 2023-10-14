package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService

private var TAG = HomeRepository::class.java.simpleName

class TopRepository(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase
) : ConfigurationRepository(networkDataSource, localDataSource.configurationDao())
{
    suspend fun getTopRatedMovies(): List<Trend>
    {
        return networkDataSource.getTopMovies().results.map()
        {
            it.toExternal(
                createBasePosterUrl(),
                createBaseBackdropUrl(),
                isBookmark(it.id)
            )
        }
    }

    suspend fun isBookmark(id: Int): Boolean
    {
        return localDataSource.bookmarkDao().isBookmark(id)
    }
}