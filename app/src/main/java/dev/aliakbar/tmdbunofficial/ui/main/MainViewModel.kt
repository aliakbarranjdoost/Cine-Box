package dev.aliakbar.tmdbunofficial.ui.main

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
import dev.aliakbar.tmdbunofficial.data.ConfigurationRepository
import dev.aliakbar.tmdbunofficial.data.HomeRepository
import dev.aliakbar.tmdbunofficial.data.source.local.LocalImageConfiguration
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

private val TAG: String = MainViewModel::class.java.simpleName

sealed interface MainUiState
{
    data class ConfigurationSuccess(val imageConfiguration: LocalImageConfiguration) : MainUiState

    object Error: MainUiState
    object Loading: MainUiState
}
class MainViewModel(
    private val configurationRepository: ConfigurationRepository,
    private val homeRepository: HomeRepository
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
                val imageConfiguration = configurationRepository.imageConfiguration
                MainUiState.ConfigurationSuccess(imageConfiguration)
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
                val configurationRepository = application.container.configurationRepository
                val trendingRepository = application.container.homeRepository
                MainViewModel(
                    configurationRepository,
                    trendingRepository
                )
            }
        }
    }
}