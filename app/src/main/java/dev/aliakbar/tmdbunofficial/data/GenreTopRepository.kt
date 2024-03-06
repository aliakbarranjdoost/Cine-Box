package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import javax.inject.Inject

class GenreTopRepository @Inject constructor(
    private val networkDataSource: TMDBApiService,
    localDataSource: TmdbDatabase
) : ConfigurationRepository(networkDataSource, localDataSource.configurationDao(), localDataSource.bookmarkDao())
{
    suspend fun getGenreTopMovies(genreId: Int, page: Int = 1): List<Trend>
    {
        return networkDataSource.getTopRatedMoviesInGenre(genreId, page).results.mapIndexed()
        { index, trend -> trend.toExternal(basePosterUrl, baseProfileUrl, "movie", index.inc()) }
    }

    suspend fun getGenreTopTvs(genreId: Int, page: Int = 1): List<Trend>
    {
        return networkDataSource.getTopRatedTvsInGenre(genreId, page).results.mapIndexed()
        { index, trend -> trend.toExternal(basePosterUrl, baseProfileUrl, "tv", index.inc()) }
    }
}