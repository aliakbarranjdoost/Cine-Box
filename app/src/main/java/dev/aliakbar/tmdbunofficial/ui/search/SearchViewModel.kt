@file:OptIn(ExperimentalCoroutinesApi::class)

package dev.aliakbar.tmdbunofficial.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aliakbar.tmdbunofficial.data.SearchRepository
import dev.aliakbar.tmdbunofficial.data.SearchResult
import dev.aliakbar.tmdbunofficial.data.source.MultiSearchPagingSource
import dev.aliakbar.tmdbunofficial.util.PAGE_SIZE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

private val TAG: String = SearchViewModel::class.java.simpleName

@HiltViewModel
class SearchViewModel @Inject constructor(val searchRepository: SearchRepository) : ViewModel()
{
    private val _query = MutableStateFlow("")

    val query = _query.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "",
        )

    private lateinit var pagingSource : MultiSearchPagingSource

    lateinit var resultPager : Flow<PagingData<SearchResult>>
    /*= query.flatMapLatest()
    {
        Pager(PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false))
        {
            MultiSearchPagingSource(query.value, searchRepository).also { pagingSource = it }
        }.flow.cachedIn(viewModelScope)
    }*/

    init
    {
        search()
    }
    fun search()
    {
        resultPager = Pager(PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false))
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
}