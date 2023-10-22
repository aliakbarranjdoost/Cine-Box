package dev.aliakbar.tmdbunofficial.ui.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.aliakbar.tmdbunofficial.TmdbUnofficialApplication
import dev.aliakbar.tmdbunofficial.data.Movie
import dev.aliakbar.tmdbunofficial.data.SearchRepository
import dev.aliakbar.tmdbunofficial.data.Trend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface SearchUiState
{
    data class Success(val results: List<Trend>) : SearchUiState
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

    private val _movies = MutableStateFlow(listOf<Movie>())
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