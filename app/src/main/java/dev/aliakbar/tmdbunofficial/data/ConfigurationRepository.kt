package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.LocalBookmarkDao
import dev.aliakbar.tmdbunofficial.data.source.local.LocalConfigurationDao
import dev.aliakbar.tmdbunofficial.data.source.local.LocalImageConfiguration
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkImageConfiguration
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

private var TAG = ConfigurationRepository::class.java.simpleName
open class ConfigurationRepository @Inject constructor(
    private val networkDataSource: TMDBApiService,
    private val localConfigurationDataSource: LocalConfigurationDao,
    private val localBookmarkDataSource: LocalBookmarkDao,
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
        localConfigurationDataSource.insert(imageConfiguration)
    }

    private suspend fun getConfigurationFromLocal(): LocalImageConfiguration
    {
        return localConfigurationDataSource.getConfiguration()
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
        return localBookmarkDataSource.isBookmark(id)
    }
}