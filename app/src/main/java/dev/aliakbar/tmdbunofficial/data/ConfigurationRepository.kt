package dev.aliakbar.tmdbunofficial.data

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
        runBlocking()
        {
            // TODO: catch should change to some sort of caching
            try
            {
                imageConfiguration = getConfigurationFromNetwork()
            }
            catch (e: Exception)
            {
                imageConfiguration = ImageConfiguration(
                    "http://image.tmdb.org/t/p/",
                    "https://image.tmdb.org/t/p/",
                    listOf("w300", "w780", "w1280", "original"),
                    listOf("w45", "w92", "w154", "w185", "w300", "w500", "original"),
                    listOf("w92", "w154", "w185", "w342", "w500", "w780", "original"),
                    listOf("w45", "w185", "h632", "original"),
                    listOf("w92", "w185", "w300", "original")
                )
            }
        }
    }

    val basePosterUrl = createBaseImageUrl(imageConfiguration.posterSizes)
    val baseBackdropUrl = createBaseImageUrl(imageConfiguration.backdropSizes)
    val baseProfileUrl = createBaseImageUrl(imageConfiguration.profileSizes)
    val baseStillUrl = createBaseImageUrl(imageConfiguration.stillSizes)
    val baseLogoUrl = createBaseImageUrl(imageConfiguration.logoSizes)

    private suspend fun getConfigurationFromNetwork(): ImageConfiguration
    {
        return networkDataSource.getConfiguration().imageConfiguration.toExternal()
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