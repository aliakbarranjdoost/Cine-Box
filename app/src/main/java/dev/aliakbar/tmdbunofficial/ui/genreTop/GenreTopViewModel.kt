package dev.aliakbar.tmdbunofficial.ui.genreTop

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aliakbar.tmdbunofficial.GenreTop
import dev.aliakbar.tmdbunofficial.data.GenreTopRepository
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.data.source.GenreTopPagingSource
import dev.aliakbar.tmdbunofficial.util.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private val TAG: String = GenreTopViewModel::class.java.simpleName

@HiltViewModel
class GenreTopViewModel @Inject constructor(
    val repository: GenreTopRepository,
    val savedStateHandle: SavedStateHandle
) : ViewModel()
{
    private val genreId = savedStateHandle[GenreTop.genreIdArg] ?: 0
    val genreName = savedStateHandle[GenreTop.genreNameArg] ?: ""
    val type = savedStateHandle[GenreTop.typeArg] ?: true

    private lateinit var pagingSource : GenreTopPagingSource

    lateinit var result : Flow<PagingData<Trend>>

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
}