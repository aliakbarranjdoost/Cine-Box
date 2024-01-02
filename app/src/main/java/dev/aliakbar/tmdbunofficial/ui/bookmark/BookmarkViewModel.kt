package dev.aliakbar.tmdbunofficial.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.aliakbar.tmdbunofficial.TmdbUnofficialApplication
import dev.aliakbar.tmdbunofficial.data.Bookmark
import dev.aliakbar.tmdbunofficial.data.BookmarkRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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
private const val TIMEOUT_MILLIS = 5_000L

class BookmarkViewModel(
    private val repository: BookmarkRepository
) : ViewModel()
{
    var bookmarkUiState: StateFlow<BookmarkUiState> = repository.getBookmarksStream()
        .map { BookmarkUiState.Success(it) }
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = BookmarkUiState.Loading
    )

    init
    {
        //getBookmarks()
    }

    /*private fun getBookmarks()
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
    }*/

    fun removeFromBookmark(bookmark: Bookmark)
    {
        viewModelScope.launch()
        {
            repository.removeFromBookmark(bookmark)
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