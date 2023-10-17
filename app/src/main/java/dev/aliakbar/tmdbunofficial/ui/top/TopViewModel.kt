package dev.aliakbar.tmdbunofficial.ui.top

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import dev.aliakbar.tmdbunofficial.ui.details.DetailsUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface TopUiState
{
    data class Success(
        val topRatedMovies: List<Trend>,
        //val topRatedSeries: List<Trend>
    ) : TopUiState

    data object Error : TopUiState
    data object Loading : TopUiState
}

private val TAG: String = TopViewModel::class.java.simpleName

class TopViewModel(private val topRepository: TopRepository) : ViewModel()
{
    var topUiState: TopUiState by mutableStateOf(TopUiState.Loading)
        private set

    init
    {
        getTopRatedMovies()
    }

    /*private fun getTopRatedMovies()
    {
        viewModelScope.launch()
        {
            topUiState = try
            {
                topRepository.getTopRatedMovies()
                TopUiState.Success(topRepository.getTopRatedMovies())
            }
            catch (e: IOException)
            {
                TopUiState.Error
            }
            catch (e: HttpException)
            {
                TopUiState.Error
            }
        }
    }*/

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