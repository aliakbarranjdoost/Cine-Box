package dev.aliakbar.tmdbunofficial.ui.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.aliakbar.tmdbunofficial.TmdbUnofficialApplication
import dev.aliakbar.tmdbunofficial.data.MultiSearchResult
import dev.aliakbar.tmdbunofficial.data.SearchRepository
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.ui.details.DetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface SearchUiState
{
    data class Success(val results: List<MultiSearchResult>) : SearchUiState
    data object Error : SearchUiState
    data object Loading : SearchUiState
}

private val TAG: String = SearchViewModel::class.java.simpleName

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel()
{
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchResult = MutableStateFlow(listOf<MultiSearchResult>())
    var searchResult = _searchResult.asStateFlow()

    var searchUiState: SearchUiState by mutableStateOf(
        SearchUiState.Loading
    )

    init
    {
        viewModelScope.launch()
        {
            searchUiState = try
            {
                Log.d(TAG, searchRepository.search().toString())
                SearchUiState.Success(searchRepository.search())
            }
            catch (e: IOException)
            {
                SearchUiState.Error
            }
            catch (e: HttpException)
            {
                SearchUiState.Error
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
                val searchRepository = application.container.searchRepository
                SearchViewModel(searchRepository)
            }
        }
    }
}