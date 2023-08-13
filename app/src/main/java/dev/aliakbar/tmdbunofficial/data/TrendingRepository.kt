package dev.aliakbar.tmdbunofficial.data

import android.util.Log
import dev.aliakbar.tmdbunofficial.data.source.local.LocalConfigurationDao
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService

private var TAG = TrendingRepository::class.java.simpleName
class TrendingRepository(
    private val networkDataSource: TMDBApiService,
    private val localDataSource: LocalConfigurationDao
): ConfigurationRepository(networkDataSource, localDataSource)
{
    init
    {
        Log.d(TAG,"TrendingRepository")
    }
}