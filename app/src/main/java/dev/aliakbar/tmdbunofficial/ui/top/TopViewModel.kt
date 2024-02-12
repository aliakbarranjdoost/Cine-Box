package dev.aliakbar.tmdbunofficial.ui.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aliakbar.tmdbunofficial.data.TopRepository
import dev.aliakbar.tmdbunofficial.data.Trend
import javax.inject.Inject

sealed interface TopUiState
{
    data class Success(
        val topRatedMovies: List<Trend>,
    ) : TopUiState

    data object Error : TopUiState
    data object Loading : TopUiState
}

private val TAG: String = TopViewModel::class.java.simpleName

@HiltViewModel
class TopViewModel @Inject constructor(val topRepository: TopRepository) : ViewModel()
{

    var getTopRatedMovies = topRepository.getTopRatedMovies().cachedIn(viewModelScope)
    var getTopRatedSeries = topRepository.getTopRatedSeries().cachedIn(viewModelScope)
}