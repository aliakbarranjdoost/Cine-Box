package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService

private var TAG = DetailsRepository::class.java.simpleName

class DetailsRepository(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase
): ConfigurationRepository(networkDataSource, localDataSource)
{
    suspend fun getMovieDetails(id: Int): Movie
    {
        return networkDataSource.getMovieDetails(id).toExternal(
            basePosterUrl = basePosterUrl,
            baseBackdropUrl = baseBackdropUrl,
            baseLogoUrl = baseLogoUrl,
            baseProfileUrl = baseProfileUrl,
            false
        )
    }

    suspend fun getTvDetails(id: Int): Tv
    {
        return networkDataSource.getTvDetails(id).toExternal(
            basePosterUrl = basePosterUrl,
            baseBackdropUrl = baseBackdropUrl,
            baseLogoUrl = baseLogoUrl,
            baseProfileUrl = baseProfileUrl,
            false
        )
    }

    suspend fun addMovieToBookmark(bookmark: Bookmark)
    {
        localDataSource.bookmarkDao().insert(bookmark.toLocal())
    }
}
