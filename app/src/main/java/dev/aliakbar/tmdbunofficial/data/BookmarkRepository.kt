package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepository @Inject constructor(
    networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase
): ConfigurationRepository(networkDataSource, localDataSource.configurationDao(), localDataSource.bookmarkDao())
{
    fun getBookmarksStream() : Flow<List<Bookmark>>
    {
        return localDataSource.bookmarkDao().getAllBookmarksStream().map { it.toExternal() }
    }

    suspend fun removeFromBookmark(bookmark: Bookmark)
    {
        localDataSource.bookmarkDao().delete(bookmark.toLocal())
    }
}