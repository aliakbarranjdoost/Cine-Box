package dev.aliakbar.tmdbunofficial.ui.movie

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
import dev.aliakbar.tmdbunofficial.ID_ARG
import dev.aliakbar.tmdbunofficial.TmdbUnofficialApplication
import dev.aliakbar.tmdbunofficial.data.DetailsRepository
import dev.aliakbar.tmdbunofficial.data.Movie
import dev.aliakbar.tmdbunofficial.data.toBookmark
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface MovieUiState
{
    data class Success(val movie: Movie) : MovieUiState
    data object Error : MovieUiState
    data object Loading : MovieUiState
}

private val TAG: String = MovieViewModel::class.java.simpleName

class MovieViewModel(
    private val repository: DetailsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel()
{
    var movieUiState: MovieUiState by mutableStateOf(
        MovieUiState.Loading
    )

    private val id: Int = savedStateHandle[ID_ARG] ?: 0

    init
    {
        getMovieDetails(id)
    }

    private fun getMovieDetails(id: Int)
    {
        viewModelScope.launch()
        {
            movieUiState = try
            {
                MovieUiState.Success(repository.getMovieDetails(id))
            }
            catch (e: IOException)
            {
                MovieUiState.Error
            }
            catch (e: HttpException)
            {
                MovieUiState.Error
            }
        }
    }

    fun addToBookmark(movie: Movie)
    {
        viewModelScope.launch()
        {
            repository.addTrendToBookmark(movie.toBookmark())
        }
    }

    fun removeFromBookmark(movie: Movie)
    {
        viewModelScope.launch()
        {
            repository.removeFromBookmark(movie.toBookmark())
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
                MovieViewModel(repository, savedStateHandle)
            }
        }
    }
}
