package dev.aliakbar.tmdbunofficial.ui.episode

import androidx.lifecycle.SavedStateHandle
import dev.aliakbar.tmdbunofficial.data.Episode

sealed interface EpisodeUiState
{
    data class Success(val episode: Episode): EpisodeUiState
    data object Loading: EpisodeUiState
    data object Error: EpisodeUiState
}

private val TAG = EpisodeViewModel:: class.java.simpleName

class EpisodeViewModel(savedStateHandle: SavedStateHandle)
{
    val episode: String = savedStateHandle["episode"] ?: ""
}