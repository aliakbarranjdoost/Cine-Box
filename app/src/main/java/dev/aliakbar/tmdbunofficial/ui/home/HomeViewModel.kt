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

sealed interface HomeUiState
{
    data class Success(val trends: List<Trend>) : HomeUiState
    data object Error : HomeUiState
    data object Loading : HomeUiState
}

class HomeViewModel(
    private val trendingRepository: TrendingRepository
) : ViewModel()
{
    private lateinit var todayTrendMovies: List<Trend>
    private lateinit var thisWeekTrendMovies: List<Trend>
    private lateinit var todayTrendSeries: List<Trend>
    private lateinit var thisWeekTrendSeries: List<Trend>
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init
    {
        viewModelScope.launch()
        {
            homeUiState = HomeUiState.Loading
            homeUiState = try
            {
                todayTrendMovies = trendingRepository.getTodayTrendingMovies().toExternal()
                thisWeekTrendMovies = trendingRepository.getThisWeekTrendingMovies().toExternal()
                todayTrendSeries = trendingRepository.getTodayTrendingSeries().toExternal()
                thisWeekTrendSeries = trendingRepository.getThisWeekTrendingSeries().toExternal()
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

    fun getTodayTrendMovies()
    {
        homeUiState = HomeUiState.Success(todayTrendMovies)
    }

    fun getThisWeekTrendMovies()
    {
        homeUiState = HomeUiState.Success(thisWeekTrendMovies)
    }

    fun getTodayTrendSeries()
    {
        homeUiState = HomeUiState.Success(todayTrendSeries)
    }

    fun getThisWeekTrendSeries()
    {
        homeUiState = HomeUiState.Success(thisWeekTrendSeries)
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