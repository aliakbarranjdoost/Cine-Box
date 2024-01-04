package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService

private val TAG: String = SearchRepository::class.java.simpleName

class SearchRepository(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase
) : ConfigurationRepository(networkDataSource, localDataSource.configurationDao())
{
    suspend fun search(query: String, page: Int = 1): List<SearchResult>
    {
        return networkDataSource.multiSearch(query, page).results.map()
        { it.toExternal(basePosterUrl, baseProfileUrl,baseProfileUrl, isBookmark(it.id)) }
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