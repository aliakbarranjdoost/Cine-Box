package dev.aliakbar.tmdbunofficial.ui.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.aliakbar.tmdbunofficial.TmdbUnofficialApplication
import dev.aliakbar.tmdbunofficial.data.HomeRepository
import dev.aliakbar.tmdbunofficial.data.TopRepository
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.ui.home.HomeUiState
import dev.aliakbar.tmdbunofficial.ui.home.HomeViewModel

sealed interface TopUiState
{
    data class Success(
        val topRatedMovies: List<Trend>,
        //val topRatedSeries: List<Trend>
    )
    data object Error : TopUiState
    data object Loading : TopUiState
}

private val TAG: String = TopViewModel::class.java.simpleName

class TopViewModel(private val topRepository: TopRepository): ViewModel()
{
    companion object
    {
        val factory: ViewModelProvider.Factory = viewModelFactory()
        {
            initializer()
            {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TmdbUnofficialApplication)
                val topRepository = application.container.topRepository
                TopViewModel(topRepository)
            }
        }
    }
}