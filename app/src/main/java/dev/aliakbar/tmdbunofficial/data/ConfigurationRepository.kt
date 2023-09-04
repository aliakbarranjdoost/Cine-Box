package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.LocalConfigurationDao
import dev.aliakbar.tmdbunofficial.data.source.local.LocalImageConfiguration
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkImageConfiguration
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import kotlinx.coroutines.runBlocking

private var TAG = ConfigurationRepository::class.java.simpleName
open class ConfigurationRepository(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: LocalConfigurationDao
)
{
    var imageConfiguration: LocalImageConfiguration
    init
    {
        runBlocking ()
        {
            var networkImageConfiguration: LocalImageConfiguration = getConfigurationFromNetwork().toLocal( 1 )
            saveConfigInLocal(networkImageConfiguration)
            imageConfiguration = getConfigurationFromLocal()
        }
    }

    private suspend fun getConfigurationFromNetwork(): NetworkImageConfiguration
    {
        return networkDataSource.getConfiguration().imageConfiguration
    }

    private suspend fun saveConfigInLocal(imageConfiguration: LocalImageConfiguration)
    {
        localDataSource.insert(imageConfiguration)
    }

    private suspend fun getConfigurationFromLocal(): LocalImageConfiguration
    {
        return localDataSource.getConfiguration()
    }

    private fun findBiggestImageSize(imageSizes: List<String>): String
    {
        return imageSizes[imageSizes.size - 2]
    }

    protected fun createBasePosterUrl(): String
    {
        return imageConfiguration.secureBaseUrl + findBiggestImageSize(imageConfiguration.posterSizes)
    }

    protected fun createBaseBackdropUrl(): String
    {
        return imageConfiguration.secureBaseUrl + findBiggestImageSize(imageConfiguration.backdropSizes)
    }

    protected fun createBaseLogoUrl(): String
    {
        return imageConfiguration.secureBaseUrl + findBiggestImageSize(imageConfiguration.logoSizes)
    }
}