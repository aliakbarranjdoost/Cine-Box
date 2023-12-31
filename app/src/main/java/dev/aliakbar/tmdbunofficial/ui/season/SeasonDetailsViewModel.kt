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
import dev.aliakbar.tmdbunofficial.TmdbUnofficialApplication
import dev.aliakbar.tmdbunofficial.data.SeasonDetailsRepository
import dev.aliakbar.tmdbunofficial.data.SeasonDetails
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface SeasonDetailsUiState
{
    data class Success(val seasonDetails: SeasonDetails): SeasonDetailsUiState
    data object Loading: SeasonDetailsUiState
    data object Error: SeasonDetailsUiState
}

private val TAG = SeasonDetailsViewModel:: class.java.simpleName
class SeasonDetailsViewModel(
    private val repository: SeasonDetailsRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel()
{
    var episodeListUiState: SeasonDetailsUiState by mutableStateOf(
        SeasonDetailsUiState.Loading
    )
    private val id: Int = savedStateHandle["id"] ?: 0
    private val seasonNumber: Int = savedStateHandle["seasonNumber"] ?: 0

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
                SeasonDetailsUiState.Success(repository.getSeasonDetails(id,seasonNumber))
            }
            catch (e: IOException)
            {
                SeasonDetailsUiState.Error
            }
            catch (e: HttpException)
            {
                SeasonDetailsUiState.Error
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
                SeasonDetailsViewModel(repository, savedStateHandle)
            }
        }
    }
}