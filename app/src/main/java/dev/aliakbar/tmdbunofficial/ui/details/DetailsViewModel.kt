package dev.aliakbar.tmdbunofficial.ui.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.aliakbar.tmdbunofficial.TmdbUnofficialApplication
import dev.aliakbar.tmdbunofficial.data.DetailsRepository
import dev.aliakbar.tmdbunofficial.data.Movie
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.data.toExternal
import dev.aliakbar.tmdbunofficial.ui.home.HomeUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface DetailsUiState
{
    data class Success(val movie: Movie) : DetailsUiState
    data object Error : DetailsUiState
    data object Loading : DetailsUiState
}

class DetailsViewModel (
    private val repository: DetailsRepository
): ViewModel()
{
    var detailsUiState: DetailsUiState by mutableStateOf(
        DetailsUiState.Loading
    )

    init
    {

    }

    fun getMovieDetails(id: Int)
    {
        viewModelScope.launch()
        {
            detailsUiState = try
            {
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

    companion object
    {
        val factory: ViewModelProvider.Factory = viewModelFactory()
        {
            initializer()
            {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TmdbUnofficialApplication)
                val repository = application.container.detailsRepository
                DetailsViewModel(repository)
            }
        }
    }
}
