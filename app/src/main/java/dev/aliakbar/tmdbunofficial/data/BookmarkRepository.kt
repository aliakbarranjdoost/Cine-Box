package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookmarkRepository(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase
): ConfigurationRepository(networkDataSource, localDataSource.configurationDao())
{
    fun getBookmarksStream() : Flow<List<Bookmark>>
    {
        return localDataSource.bookmarkDao().getAllBookmarksStream().map { it.toExternal() }
    }

    suspend fun getBookmarks() : List<Bookmark>
    {
        return localDataSource.bookmarkDao().getAllBookmarks().map { it.toExternal() }
    }
}