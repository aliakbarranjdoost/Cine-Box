package dev.aliakbar.tmdbunofficial.ui.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.aliakbar.tmdbunofficial.TmdbUnofficialApplication
import dev.aliakbar.tmdbunofficial.data.ImageConfiguration
import dev.aliakbar.tmdbunofficial.data.NetworkConfigurationRepository
import dev.aliakbar.tmdbunofficial.data.NetworkTrendingRepository
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkTrending
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkTrendingMovie
import dev.aliakbar.tmdbunofficial.data.toExternal
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

private val TAG: String = MainViewModel::class.java.simpleName

sealed interface MainUiState
{
    //data class ConfigurationSuccess(val imageConfiguration: ImageConfiguration) : MainUiState
    data class TrendingTodayMoviesSuccess(val networkTrending: NetworkTrending<NetworkTrendingMovie>) :
        MainUiState

    object Error: MainUiState
    object Loading: MainUiState
}
class MainViewModel(
    private val networkConfigurationRepository: NetworkConfigurationRepository,
    private val trendingRepository: NetworkTrendingRepository
): ViewModel()
{
    var mainUiState: MainUiState by mutableStateOf(MainUiState.Loading)
        private set

    init
    {
        getConfiguration()
    }

    private fun getConfiguration()
    {
        viewModelScope.launch()
        {
            mainUiState = MainUiState.Loading
            mainUiState = try
            {
                val trendingMovie = trendingRepository.getTrendingThisWeekMovies()
                Log.d(TAG,trendingMovie.toString())
                MainUiState.TrendingTodayMoviesSuccess(trendingMovie)

                //val imageConfiguration = networkConfigurationRepository.getConfiguration().imageConfiguration.toExternal()
                //MainUiState.ConfigurationSuccess(imageConfiguration)
            }
            catch (e: IOException)
            {
                MainUiState.Error
            }
            catch (e: HttpException)
            {
                MainUiState.Error
            }
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
                val trendingRepository = application.container.networkTrendingRepository
                MainViewModel(
                    networkConfigurationRepository = networkConfigurationRepository,
                    trendingRepository = trendingRepository)
            }
        }
    }
}