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
import dev.aliakbar.tmdbunofficial.Episode
import dev.aliakbar.tmdbunofficial.ID_ARG
import dev.aliakbar.tmdbunofficial.TmdbUnofficialApplication
import dev.aliakbar.tmdbunofficial.data.EpisodeDetails
import dev.aliakbar.tmdbunofficial.data.EpisodeRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface EpisodeUiState
{
    data class Success(val episode: EpisodeDetails): EpisodeUiState
    data object Loading: EpisodeUiState
    data object Error: EpisodeUiState
}

private val TAG = EpisodeViewModel:: class.java.simpleName

class EpisodeViewModel(
    private val repository: EpisodeRepository,
    savedStateHandle: SavedStateHandle): ViewModel()
{
    private val id: Int = savedStateHandle[ID_ARG] ?: 0
    private val seasonNumber: Int = savedStateHandle[Episode.seasonNumberArg] ?: 0
    private val episodeNumber: Int = savedStateHandle[Episode.episodeNumberArg] ?: 0

    var episodeUiState: EpisodeUiState by mutableStateOf(
        EpisodeUiState.Loading
    )

    init
    {
        getEpisodeDetails()
    }

    private fun getEpisodeDetails()
    {
        viewModelScope.launch()
        {
            episodeUiState = try
            {
                EpisodeUiState.Success(repository.getEpisodeDetails(id, seasonNumber, episodeNumber))
            }
            catch (e: IOException)
            {
                EpisodeUiState.Error
            }
            catch (e: HttpException)
            {
                EpisodeUiState.Error
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
                val episodeRepository = application.container.episodeRepository
                val savedStateHandle = this.createSavedStateHandle()
                EpisodeViewModel(episodeRepository, savedStateHandle)
            }
        }
    }
}