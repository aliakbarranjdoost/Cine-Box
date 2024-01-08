package dev.aliakbar.tmdbunofficial.ui.tv

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
import dev.aliakbar.tmdbunofficial.data.Tv
import dev.aliakbar.tmdbunofficial.data.toBookmark
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface TvUiState
{
    data class Success(val tv: Tv) : TvUiState
    data object Error : TvUiState
    data object Loading : TvUiState
}

private val TAG: String = TvViewModel::class.java.simpleName

class TvViewModel(
    private val repository: DetailsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel()
{
    var tvUiState: TvUiState by mutableStateOf(TvUiState.Loading)

    private val id: Int = savedStateHandle["id"] ?: 0

    init
    {
        getTvDetails(id)
    }

    fun getTvDetails(id: Int)
    {
        viewModelScope.launch()
        {
            tvUiState = try
            {
                TvUiState.Success(repository.getTvDetails(id))
            }
            catch (e: IOException)
            {
                TvUiState.Error
            }
            catch (e: HttpException)
            {
                TvUiState.Error
            }
        }
    }

    fun addToBookmark(tv: Tv)
    {
        viewModelScope.launch()
        {
            repository.addTrendToBookmark(tv.toBookmark())
        }
    }

    fun removeFromBookmark(tv: Tv)
    {
        viewModelScope.launch()
        {
            repository.removeFromBookmark(tv.toBookmark())
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
                TvViewModel(repository, savedStateHandle)
            }
        }
    }
}
