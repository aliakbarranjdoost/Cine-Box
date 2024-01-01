package dev.aliakbar.tmdbunofficial.ui.episode

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.aliakbar.tmdbunofficial.TmdbUnofficialApplication
import dev.aliakbar.tmdbunofficial.data.Episode
import dev.aliakbar.tmdbunofficial.ui.home.HomeViewModel
import dev.aliakbar.tmdbunofficial.util.toEpisode
import kotlin.math.log

sealed interface EpisodeUiState
{
    data class Success(val episode: Episode): EpisodeUiState
    data object Loading: EpisodeUiState
    data object Error: EpisodeUiState
}

private val TAG = EpisodeViewModel:: class.java.simpleName

class EpisodeViewModel(savedStateHandle: SavedStateHandle): ViewModel()
{
    private val id: Int = savedStateHandle["seriesId"] ?: 0
    private val seasonNumber: Int = savedStateHandle["seasonNumber"] ?: 0
    private val episodeNumber: Int = savedStateHandle["episodeNumber"] ?: 0

    init
    {
        Log.d(TAG, id.toString() + seasonNumber.toString() + episodeNumber.toString())
    }

    companion object
    {
        val factory: ViewModelProvider.Factory = viewModelFactory()
        {
            initializer()
            {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TmdbUnofficialApplication)
                //val trendingRepository = application.container.homeRepository
                val savedStateHandle = this.createSavedStateHandle()
                EpisodeViewModel(savedStateHandle)
            }
        }
    }
}