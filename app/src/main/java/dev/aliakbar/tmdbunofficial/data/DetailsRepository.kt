package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import javax.inject.Inject

private var TAG = DetailsRepository::class.java.simpleName

class DetailsRepository @Inject constructor(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase
): ConfigurationRepository(networkDataSource)
{
    suspend fun getMovieDetails(id: Int): Movie
    {
        return networkDataSource.getMovieDetails(id).toExternal(
            basePosterUrl = basePosterUrl,
            baseBackdropUrl = baseBackdropUrl,
            baseLogoUrl = baseLogoUrl,
            baseProfileUrl = baseProfileUrl,
            isBookmark = isBookmark(id)
        )
    }

    suspend fun getTvDetails(id: Int): Tv
    {
        return networkDataSource.getTvDetails(id).toExternal(
            basePosterUrl = basePosterUrl,
            baseBackdropUrl = baseBackdropUrl,
            baseProfileUrl = baseProfileUrl,
            isBookmark(id)
        )
    }

    suspend fun addTrendToBookmark(bookmark: Bookmark)
    {
        localDataSource.bookmarkDao().insert(bookmark.toLocal())
    }

    suspend fun removeFromBookmark(bookmark: Bookmark)
    {
        localDataSource.bookmarkDao().delete(bookmark.toLocal())
    }

    suspend fun isBookmark(id: Int): Boolean
    {
        return localDataSource.bookmarkDao().isBookmark(id)
    }
}
