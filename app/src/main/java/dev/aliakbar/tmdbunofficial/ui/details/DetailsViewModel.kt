package dev.aliakbar.tmdbunofficial.ui.details

import android.util.Log
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
import dev.aliakbar.tmdbunofficial.data.Bookmark
import dev.aliakbar.tmdbunofficial.data.DetailsRepository
import dev.aliakbar.tmdbunofficial.data.Movie
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface DetailsUiState
{
    data class Success(val movie: Movie) : DetailsUiState
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

    init
    {
        getMovieDetails(id)
    }

    fun getMovieDetails(id: Int)
    {
        viewModelScope.launch()
        {
            detailsUiState = try
            {
                Log.d(TAG, repository.getMovieDetails(id).recommendations.toString())
                DetailsUiState.Success(repository.getMovieDetails(id))
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

    fun addMovieToBookmark(
        id: Int,
        title: String,
        score: Float,
        poster: String,
        backdrop: String
    )
    {
        viewModelScope.launch()
        {
            repository.addMovieToBookmark(
                Bookmark(
                    id = id,
                    title = title,
                    score = score,
                    poster = poster,
                    backdrop = backdrop
                )
            )
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
