package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.LocalImageConfiguration
import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkImageConfiguration
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import kotlinx.coroutines.runBlocking

private var TAG = ConfigurationRepository::class.java.simpleName
open class ConfigurationRepository(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: TmdbDatabase
)
{
    var imageConfiguration: LocalImageConfiguration

    init
    {
        runBlocking ()
        {
            val networkImageConfiguration: LocalImageConfiguration = getConfigurationFromNetwork().toLocal( 1 )
            saveConfigInLocal(networkImageConfiguration)
            imageConfiguration = getConfigurationFromLocal()
        }
    }

    val basePosterUrl = createBasePosterUrl()
    val baseBackdropUrl = createBaseBackdropUrl()
    val baseProfileUrl = createBaseProfileUrl()
    val baseStillUrl = createBaseStillUrl()
    val baseLogoUrl = createBaseLogoUrl()

    private suspend fun getConfigurationFromNetwork(): NetworkImageConfiguration
    {
        return networkDataSource.getConfiguration().imageConfiguration
    }

    private suspend fun saveConfigInLocal(imageConfiguration: LocalImageConfiguration)
    {
        localDataSource.configurationDao().insert(imageConfiguration)
    }

    private suspend fun getConfigurationFromLocal(): LocalImageConfiguration
    {
        return localDataSource.configurationDao().getConfiguration()
    }

    private fun findBiggestImageSize(imageSizes: List<String>): String
    {
        return imageSizes[imageSizes.size - 2]
    }

    private fun createBasePosterUrl(): String
    {
        return imageConfiguration.secureBaseUrl + findBiggestImageSize(imageConfiguration.posterSizes)
    }

    private fun createBaseBackdropUrl(): String
    {
        return imageConfiguration.secureBaseUrl + findBiggestImageSize(imageConfiguration.backdropSizes)
    }

    private fun createBaseLogoUrl(): String
    {
        return imageConfiguration.secureBaseUrl + findBiggestImageSize(imageConfiguration.logoSizes)
    }

    private fun createBaseProfileUrl(): String
    {
        return imageConfiguration.secureBaseUrl + findBiggestImageSize(imageConfiguration.profileSizes)
    }

    private fun createBaseStillUrl(): String
    {
        return imageConfiguration.secureBaseUrl + findBiggestImageSize(imageConfiguration.stillSizes)
    }

    suspend fun isBookmark(id: Int): Boolean
    {
        return localDataSource.bookmarkDao().isBookmark(id)
    }
}