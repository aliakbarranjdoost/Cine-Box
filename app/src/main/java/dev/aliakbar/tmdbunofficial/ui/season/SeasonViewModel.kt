package dev.aliakbar.tmdbunofficial.ui.season

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aliakbar.tmdbunofficial.ID_ARG
import dev.aliakbar.tmdbunofficial.Season
import dev.aliakbar.tmdbunofficial.data.SeasonDetails
import dev.aliakbar.tmdbunofficial.data.SeasonRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

sealed interface SeasonUiState
{
    data class Success(val seasonDetails: SeasonDetails): SeasonUiState
    data object Loading: SeasonUiState
    data object Error: SeasonUiState
}

private val TAG = SeasonViewModel:: class.java.simpleName

@HiltViewModel
class SeasonViewModel @Inject constructor(
    val repository: SeasonRepository,
    val savedStateHandle: SavedStateHandle
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

    fun getSeasonDetails()
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
}