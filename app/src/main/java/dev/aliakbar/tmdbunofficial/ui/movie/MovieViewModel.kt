package dev.aliakbar.tmdbunofficial.ui.movie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aliakbar.tmdbunofficial.ID_ARG
import dev.aliakbar.tmdbunofficial.data.DetailsRepository
import dev.aliakbar.tmdbunofficial.data.Movie
import dev.aliakbar.tmdbunofficial.data.toBookmark
import dev.aliakbar.tmdbunofficial.util.mergeSimilarItems
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

sealed interface MovieUiState
{
    data class Success(val movie: Movie) : MovieUiState
    data object Error : MovieUiState
    data object Loading : MovieUiState
}

private val TAG: String = MovieViewModel::class.java.simpleName

@HiltViewModel
class MovieViewModel @Inject constructor(
    val repository: DetailsRepository,
    val savedStateHandle: SavedStateHandle
) : ViewModel()
{
    var movieUiState: MovieUiState by mutableStateOf(MovieUiState.Loading)

    private val id: Int = savedStateHandle[ID_ARG] ?: 0

    init
    {
        getMovieDetails()
    }

    fun getMovieDetails()
    {
        viewModelScope.launch()
        {
            movieUiState = try
            {
                mergeSimilarItems(repository.getMovieDetails(id).crews)
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
}
