package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.network.NetworkImageConfiguration
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import dev.aliakbar.tmdbunofficial.util.IMAGE_QUALITY_LEVEL
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

private var TAG = ConfigurationRepository::class.java.simpleName

open class ConfigurationRepository @Inject constructor(
    private val networkDataSource: TMDBApiService
)
{
    private var imageConfiguration: ImageConfiguration

    init
    {
        runBlocking ()
        {
            imageConfiguration = getConfigurationFromNetwork().toExternal()
        }
    }

    val basePosterUrl = createBaseImageUrl(imageConfiguration.posterSizes)
    val baseBackdropUrl = createBaseImageUrl(imageConfiguration.backdropSizes)
    val baseProfileUrl = createBaseImageUrl(imageConfiguration.profileSizes)
    val baseStillUrl = createBaseImageUrl(imageConfiguration.stillSizes)
    val baseLogoUrl = createBaseImageUrl(imageConfiguration.logoSizes)

    private suspend fun getConfigurationFromNetwork(): NetworkImageConfiguration
    {
        return networkDataSource.getConfiguration().imageConfiguration
    }

    private fun findBiggestImageSize(imageSizes: List<String>): String
    {
        return imageSizes[imageSizes.size - IMAGE_QUALITY_LEVEL]
    }

    private fun createBaseImageUrl(imageSizes: List<String>): String
    {
        return imageConfiguration.secureBaseUrl + findBiggestImageSize(imageSizes)
    }
}