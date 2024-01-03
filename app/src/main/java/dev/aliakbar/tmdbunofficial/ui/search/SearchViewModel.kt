@file:OptIn(ExperimentalCoroutinesApi::class)

package dev.aliakbar.tmdbunofficial.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dev.aliakbar.tmdbunofficial.TmdbUnofficialApplication
import dev.aliakbar.tmdbunofficial.data.SearchRepository
import dev.aliakbar.tmdbunofficial.data.source.MultiSearchPagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

private const val PAGE_SIZE = 20

private val TAG: String = SearchViewModel::class.java.simpleName

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel()
{
    private val _query = MutableStateFlow("")

    val query = _query.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "",
        )

    private lateinit var pagingSource : MultiSearchPagingSource

    @OptIn(ExperimentalCoroutinesApi::class)
    val resultPager = query.flatMapLatest()
    {
        Pager(PagingConfig(pageSize = PAGE_SIZE))
        {
            MultiSearchPagingSource(query.value, searchRepository).also { pagingSource = it }
        }.flow.cachedIn(viewModelScope)
    }

    fun setQuery(query: String) {
        _query.value = query
    }

    fun invalidateDataSource() {
        pagingSource.invalidate()
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