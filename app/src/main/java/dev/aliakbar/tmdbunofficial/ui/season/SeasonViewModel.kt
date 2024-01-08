package dev.aliakbar.tmdbunofficial.ui.season

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.aliakbar.tmdbunofficial.ID_ARG
import dev.aliakbar.tmdbunofficial.Season
import dev.aliakbar.tmdbunofficial.TmdbUnofficialApplication
import dev.aliakbar.tmdbunofficial.data.SeasonDetails
import dev.aliakbar.tmdbunofficial.data.SeasonDetailsRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface SeasonUiState
{
    data class Success(val seasonDetails: SeasonDetails): SeasonUiState
    data object Loading: SeasonUiState
    data object Error: SeasonUiState
}

private val TAG = SeasonViewModel:: class.java.simpleName
class SeasonViewModel(
    private val repository: SeasonDetailsRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel()
{
    var episodeListUiState: SeasonUiState by mutableStateOf(
        SeasonUiState.Loading
    )
    val id: Int = savedStateHandle[ID_ARG] ?: 0
    private val seasonNumber: Int = savedStateHandle[Season.seasonNumberArg] ?: 0

    init
    {
        getSeasonDetails()
    }

    private fun getSeasonDetails()
    {
        viewModelScope.launch()
        {
            episodeListUiState = try
            {
                SeasonUiState.Success(repository.getSeasonDetails(id,seasonNumber))
            }
            catch (e: IOException)
            {
                SeasonUiState.Error
            }
            catch (e: HttpException)
            {
                SeasonUiState.Error
            }
        }
    }

    companion object
    {
        val factory: ViewModelProvider.Factory = viewModelFactory()
        {
            initializer()
            {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TmdbUnofficialApplication)
                val repository = application.container.seasonDetailsRepository
                val savedStateHandle = this.createSavedStateHandle()
                SeasonViewModel(repository, savedStateHandle)
            }
        }
    }
}