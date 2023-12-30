package dev.aliakbar.tmdbunofficial.ui.episode

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
import dev.aliakbar.tmdbunofficial.data.DetailsRepository
import dev.aliakbar.tmdbunofficial.data.Episode
import dev.aliakbar.tmdbunofficial.data.EpisodeListRepository
import dev.aliakbar.tmdbunofficial.data.SeasonDetails
import dev.aliakbar.tmdbunofficial.ui.details.DetailsUiState
import dev.aliakbar.tmdbunofficial.ui.details.DetailsViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface EpisodeListUiState
{
    data class Success(val seasonDetails: SeasonDetails): EpisodeListUiState
    data object Loading: EpisodeListUiState
    data object Error: EpisodeListUiState
}

private val TAG = EpisodeListViewModel:: class.java.simpleName
class EpisodeListViewModel(
    private val repository: EpisodeListRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel()
{
    var episodeListUiState: EpisodeListUiState by mutableStateOf(
        EpisodeListUiState.Loading
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
                EpisodeListUiState.Success(repository.getSeasonDetails(id,seasonNumber))
            }
            catch (e: IOException)
            {
                EpisodeListUiState.Error
            }
            catch (e: HttpException)
            {
                EpisodeListUiState.Error
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
                val repository = application.container.episodeListRepository
                val savedStateHandle = this.createSavedStateHandle()
                EpisodeListViewModel(repository, savedStateHandle)
            }
        }
    }
}