package dev.aliakbar.tmdbunofficial.ui.details

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
import dev.aliakbar.tmdbunofficial.data.Movie
import dev.aliakbar.tmdbunofficial.data.SeasonDetails
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.data.Tv
import dev.aliakbar.tmdbunofficial.data.toBookmark
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface DetailsUiState
{
    data class SuccessMovie(val movie: Movie) : DetailsUiState
    data class SuccessTv(val tv: Tv) : DetailsUiState
    data object Error : DetailsUiState
    data object Loading : DetailsUiState
}

private val TAG: String = DetailsViewModel::class.java.simpleName

class DetailsViewModel(
    private val repository: DetailsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel()
{
    var detailsUiState: DetailsUiState by mutableStateOf(
        DetailsUiState.Loading
    )

    private val id: Int = savedStateHandle["id"] ?: 0
    private val type: Boolean = savedStateHandle["type"] ?: true

    init
    {
        if (type)
        {
            getMovieDetails(id)
        }
        else
        {
            getTvDetails(id)
        }
    }

    fun getMovieDetails(id: Int)
    {
        viewModelScope.launch()
        {
            detailsUiState = try
            {
                DetailsUiState.SuccessMovie(repository.getMovieDetails(id))
            }
            catch (e: IOException)
            {
                DetailsUiState.Error
            }
            catch (e: HttpException)
            {
                DetailsUiState.Error
            }
        }
    }

    fun getTvDetails(id: Int)
    {
        viewModelScope.launch()
        {
            detailsUiState = try
            {
                DetailsUiState.SuccessTv(repository.getTvDetails(id))
            }
            catch (e: IOException)
            {
                DetailsUiState.Error
            }
            catch (e: HttpException)
            {
                DetailsUiState.Error
            }
        }
    }

    fun addMovieToBookmark(trend: Trend)
    {
        viewModelScope.launch()
        {
            repository.addMovieToBookmark(trend.toBookmark())
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
                val repository = application.container.detailsRepository
                val savedStateHandle = this.createSavedStateHandle()
                DetailsViewModel(repository, savedStateHandle)
            }
        }
    }
}
