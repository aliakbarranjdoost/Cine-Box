package dev.aliakbar.tmdbunofficial.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.aliakbar.tmdbunofficial.TmdbUnofficialApplication
import dev.aliakbar.tmdbunofficial.data.NetworkConfigurationRepository
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkConfiguration
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import kotlinx.coroutines.launch

private val TAG: String = MainViewModel::class.java.simpleName
class MainViewModel(
    private val networkConfigurationRepository: NetworkConfigurationRepository
): ViewModel()
{
    init
    {
        getConfiguration()
    }

    private fun getConfiguration()
    {
        viewModelScope.launch()
        {
            val configuration = networkConfigurationRepository.getConfiguration()
            Log.d(TAG,configuration.toString())
        }
    }

    companion object
    {
        val factory: ViewModelProvider.Factory = viewModelFactory()
        {
            initializer()
            {
                val application =(this[APPLICATION_KEY] as TmdbUnofficialApplication)
                val networkConfigurationRepository = application.container.networkConfigurationRepository
                MainViewModel(networkConfigurationRepository = networkConfigurationRepository)
            }
        }
    }
}