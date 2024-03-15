package dev.aliakbar.tmdbunofficial.ui.person

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aliakbar.tmdbunofficial.ID_ARG
import dev.aliakbar.tmdbunofficial.data.PersonDetails
import dev.aliakbar.tmdbunofficial.data.PersonRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

sealed interface PersonUiState
{
    data class Success(val person: PersonDetails) : PersonUiState
    data object Error : PersonUiState
    data object Loading : PersonUiState
}

private val TAG: String = PersonViewModel::class.java.simpleName

@HiltViewModel
class PersonViewModel @Inject constructor(
    val repository: PersonRepository,
    val savedStateHandle: SavedStateHandle
): ViewModel()
{
    var personUiState: PersonUiState by mutableStateOf(PersonUiState.Loading)

    private val id: Int = savedStateHandle[ID_ARG] ?: 0

    init
    {
        getPerson()
    }

    fun getPerson()
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
}