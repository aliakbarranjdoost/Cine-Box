package dev.aliakbar.tmdbunofficial.ui.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.aliakbar.tmdbunofficial.TmdbUnofficialApplication
import dev.aliakbar.tmdbunofficial.data.TopRepository
import dev.aliakbar.tmdbunofficial.data.Trend
import kotlinx.coroutines.flow.Flow

sealed interface TopUiState
{
    data class Success(
        val topRatedMovies: List<Trend>,
    ) : TopUiState

    data object Error : TopUiState
    data object Loading : TopUiState
}

private val TAG: String = TopViewModel::class.java.simpleName

class TopViewModel(private val topRepository: TopRepository) : ViewModel()
{
    fun getTopRatedMovies(): Flow<PagingData<Trend>> = topRepository.getTopRatedMovies().cachedIn(viewModelScope)
    fun getTopRatedSeries(): Flow<PagingData<Trend>> = topRepository.getTopRatedSeries().cachedIn(viewModelScope)

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