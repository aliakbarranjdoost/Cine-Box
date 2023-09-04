package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkMovieDetails
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService

private var TAG = DetailsRepository::class.java.simpleName

class DetailsRepository(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase
): ConfigurationRepository(networkDataSource, localDataSource.configurationDao())
{
    suspend fun getMovieDetails(id: Int): Movie
    {
        return networkDataSource.getMovieDetails(id).toExternal(
            basePosterUrl = createBasePosterUrl(),
            baseBackdropUrl = createBaseBackdropUrl(),
            baseLogoUrl = createBaseLogoUrl()
        )
    }
}
