package dev.aliakbar.tmdbunofficial.ui.person

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.aliakbar.tmdbunofficial.TmdbUnofficialApplication
import dev.aliakbar.tmdbunofficial.data.PersonDetails
import dev.aliakbar.tmdbunofficial.data.PersonRepository
import dev.aliakbar.tmdbunofficial.ui.movie.MovieUiState

sealed interface PersonUiState
{
    data class Success(val person: PersonDetails) : PersonUiState
    data object Error : PersonUiState
    data object Loading : PersonUiState
}

private val TAG: String = PersonViewModel::class.java.simpleName

class PersonViewModel(
    private val repository: PersonRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel()
{
    var movieUiState: MovieUiState by mutableStateOf(
        MovieUiState.Loading
    )

    companion object
    {
        val factory: ViewModelProvider.Factory = viewModelFactory()
        {
            initializer()
            {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TmdbUnofficialApplication)
                val repository = application.container.personRepository
                val savedStateHandle = this.createSavedStateHandle()
                PersonViewModel(repository, savedStateHandle)
            }
        }
    }
}