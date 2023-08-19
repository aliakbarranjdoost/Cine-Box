package dev.aliakbar.tmdbunofficial.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.aliakbar.tmdbunofficial.TmdbUnofficialApplication
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.data.TrendingRepository
import dev.aliakbar.tmdbunofficial.data.toExternal
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

enum class TrendType
{
    TODAY_MOVIES,THIS_WEEK_MOVIES,TODAY_SERIES,THIS_WEEK_SERIES
}

sealed interface HomeUiState
{
    data class Success(val trends: List<Trend>) : HomeUiState
    data class Error(val trendType: TrendType): HomeUiState
    data class Loading(val trendType: TrendType): HomeUiState
}

class HomeViewModel(
    private val trendingRepository: TrendingRepository
) : ViewModel()
{
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading(TrendType.TODAY_MOVIES))
        private set

    init
    {
        viewModelScope.launch()
        {
            getTrendingTodayMovies()
        }
    }
    private fun getTrendingTodayMovies()
    {
        viewModelScope.launch()
        {
            homeUiState = HomeUiState.Loading(TrendType.TODAY_MOVIES)
            homeUiState = try
            {
                val todayTrendMovies = trendingRepository.getTodayTrendingMovies()
                HomeUiState.Success(todayTrendMovies.toExternal())
            }
            catch (e: IOException)
            {
                HomeUiState.Error(TrendType.TODAY_MOVIES)
            }
            catch (e: HttpException)
            {
                HomeUiState.Error(TrendType.TODAY_MOVIES)
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
                val trendingRepository = application.container.trendingRepository
                HomeViewModel(trendingRepository)
            }
        }
    }
}