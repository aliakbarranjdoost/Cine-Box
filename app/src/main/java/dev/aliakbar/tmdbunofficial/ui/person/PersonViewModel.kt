package dev.aliakbar.tmdbunofficial.ui.person

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
import dev.aliakbar.tmdbunofficial.data.PersonDetails
import dev.aliakbar.tmdbunofficial.data.PersonRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

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
    var personUiState: PersonUiState by mutableStateOf(
        PersonUiState.Loading
    )

    private val id: Int = savedStateHandle[ID_ARG] ?: 0

    init
    {
        getPerson()
    }

    private fun getPerson()
    {
        viewModelScope.launch()
        {
            personUiState = try
            {
                PersonUiState.Success(repository.getPerson(id))
            }
            catch (e: IOException)
            {
                PersonUiState.Error
            }
            catch (e: HttpException)
            {
                PersonUiState.Error
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
                val repository = application.container.personRepository
                val savedStateHandle = this.createSavedStateHandle()
                PersonViewModel(repository, savedStateHandle)
            }
        }
    }
}