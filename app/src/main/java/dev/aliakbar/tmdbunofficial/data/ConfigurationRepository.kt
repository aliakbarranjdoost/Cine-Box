package dev.aliakbar.tmdbunofficial.data

import android.util.Log
import dev.aliakbar.tmdbunofficial.data.source.local.LocalConfigurationDao
import dev.aliakbar.tmdbunofficial.data.source.local.LocalImageConfiguration
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkImageConfiguration
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.internal.wait

private var TAG = ConfigurationRepository::class.java.simpleName
class ConfigurationRepository(
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

    private suspend fun getConfigurationFromLocal(id: Int = 1): LocalImageConfiguration
    {
        return localDataSource.getConfiguration(id)
    }
}