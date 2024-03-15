package dev.aliakbar.tmdbunofficial.ui.episode

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aliakbar.tmdbunofficial.Episode
import dev.aliakbar.tmdbunofficial.ID_ARG
import dev.aliakbar.tmdbunofficial.data.EpisodeDetails
import dev.aliakbar.tmdbunofficial.data.EpisodeRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

sealed interface EpisodeUiState
{
    data class Success(val episode: EpisodeDetails): EpisodeUiState
    data object Loading: EpisodeUiState
    data object Error: EpisodeUiState
}

private val TAG = EpisodeViewModel:: class.java.simpleName

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    val repository: EpisodeRepository,
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

    fun getEpisodeDetails()
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
}