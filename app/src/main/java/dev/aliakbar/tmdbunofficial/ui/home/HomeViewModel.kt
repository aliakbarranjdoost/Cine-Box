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
import dev.aliakbar.tmdbunofficial.ui.main.MainUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface HomeUiState
{
    data class Success(val trends: List<Trend>): HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}

class HomeViewModel(
    private val trendingRepository: TrendingRepository
) : ViewModel()
{
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init
    {
        getTrendingTodayMovies()
    }
    private fun getTrendingTodayMovies()
    {
        viewModelScope.launch()
        {
            homeUiState = HomeUiState.Loading
            homeUiState = try
            {
                val todayTrendMovies = trendingRepository.getTodayTrendingMovies()
                HomeUiState.Success(todayTrendMovies)
            }
            catch (e: IOException)
            {
                HomeUiState.Error
            }
            catch (e: HttpException)
            {
                HomeUiState.Error
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