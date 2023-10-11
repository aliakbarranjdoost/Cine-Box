package dev.aliakbar.tmdbunofficial.ui.bookmark

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.aliakbar.tmdbunofficial.TmdbUnofficialApplication
import dev.aliakbar.tmdbunofficial.data.Bookmark
import dev.aliakbar.tmdbunofficial.data.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BookmarkUiState
{
    data class Success(val bookmarks: List<Bookmark>) : BookmarkUiState
    data object Error : BookmarkUiState
    data object Loading : BookmarkUiState
}

private val TAG: String = BookmarkViewModel::class.java.simpleName

class BookmarkViewModel(
    private val repository: BookmarkRepository
) : ViewModel()
{
    var bookmarkUiState: BookmarkUiState by mutableStateOf(BookmarkUiState.Loading)
        private set

    /*repository.getBookmarks()
    .map { BookmarkUiState.Success(it) }
    .stateIn(scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BookmarkUiState.Loading)*/

    init
    {
        getBookmarks()
    }

    fun getBookmarks()
    {
        viewModelScope.launch()
        {
            bookmarkUiState = try
            {
                BookmarkUiState.Success(repository.getBookmarks())
            }
            catch (e: IOException)
            {
                BookmarkUiState.Error
            }
            catch (e: HttpException)
            {
                BookmarkUiState.Error
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
                val repository = application.container.bookmarkRepository
                BookmarkViewModel(repository)
            }
        }
    }
}