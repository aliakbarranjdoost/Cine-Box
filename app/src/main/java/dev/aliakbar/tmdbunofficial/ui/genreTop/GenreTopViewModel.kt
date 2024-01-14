package dev.aliakbar.tmdbunofficial.ui.genreTop

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.aliakbar.tmdbunofficial.GenreTop
import dev.aliakbar.tmdbunofficial.TmdbUnofficialApplication
import dev.aliakbar.tmdbunofficial.data.GenreTopRepository
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.data.source.GenreTopPagingSource
import dev.aliakbar.tmdbunofficial.data.toBookmark
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

private const val PAGE_SIZE = 20

private val TAG: String = GenreTopViewModel::class.java.simpleName

class GenreTopViewModel(
    private val repository: GenreTopRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel()
{
    val genreId = savedStateHandle[GenreTop.genreIdArg] ?: 0
    val genreName = savedStateHandle[GenreTop.genreNameArg] ?: ""
    val type = savedStateHandle[GenreTop.typeArg] ?: true

    private lateinit var pagingSource : GenreTopPagingSource

    lateinit var result : Flow<PagingData<Trend>>
    /*= query.flatMapLatest()
    {
        Pager(PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false))
        {
            MultiSearchPagingSource(query.value, searchRepository).also { pagingSource = it }
        }.flow.cachedIn(viewModelScope)
    }*/

    init
    {
        if (type)
        {
            getGenreTopRatedMovies()
        }
        else
        {
            getGenreTopRatedTvs()
        }
    }
    private fun getGenreTopRatedMovies()
    {
        result = Pager(PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false))
        {
            GenreTopPagingSource(genreId, type, repository ).also { pagingSource = it }
        }.flow.cachedIn(viewModelScope)
    }

    private fun getGenreTopRatedTvs()
    {
        result = Pager(PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false))
        {
            GenreTopPagingSource(genreId, type, repository ).also { pagingSource = it }
        }.flow.cachedIn(viewModelScope)
    }

    fun invalidateDataSource() {
        pagingSource.invalidate()
    }

    fun addToBookmark(movie: Trend)
    {
        viewModelScope.launch()
        {
            repository.addTrendToBookmark(movie.toBookmark())
        }
    }

    fun removeFromBookmark(movie: Trend)
    {
        viewModelScope.launch()
        {
            repository.removeFromBookmark(movie.toBookmark())
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
                val repository = application.container.genreTopRepository
                val savedStateHandle = this.createSavedStateHandle()
                GenreTopViewModel(repository, savedStateHandle)
            }
        }
    }
}