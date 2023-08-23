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
    private lateinit var popularMovies: List<Trend>

    var homeMovieUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set
    var homeSerialUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set
    var homePopularMoviesUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init
    {
        viewModelScope.launch()
        {
            homeMovieUiState = HomeUiState.Loading
            homeMovieUiState = try
            {
                todayTrendMovies = trendingRepository.getTodayTrendingMovies().toExternal()
                thisWeekTrendMovies = trendingRepository.getThisWeekTrendingMovies().toExternal()
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

            homeSerialUiState = HomeUiState.Loading
            homeSerialUiState = try
            {
                todayTrendSeries = trendingRepository.getTodayTrendingSeries().toExternal()
                thisWeekTrendSeries = trendingRepository.getThisWeekTrendingSeries().toExternal()
                HomeUiState.Success(todayTrendSeries)
            }
            catch (e: IOException)
            {
                HomeUiState.Error
            }
            catch (e: HttpException)
            {
                HomeUiState.Error
            }

            homePopularMoviesUiState = HomeUiState.Loading
            homePopularMoviesUiState = try
            {
                popularMovies = trendingRepository.getPopularMovies().toExternal()
                HomeUiState.Success(popularMovies)
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
        homeMovieUiState = HomeUiState.Success(todayTrendMovies)
    }

    fun getThisWeekTrendMovies()
    {
        homeMovieUiState = HomeUiState.Success(thisWeekTrendMovies)
    }

    fun getTodayTrendSeries()
    {
        homeSerialUiState = HomeUiState.Success(todayTrendSeries)
    }

    fun getThisWeekTrendSeries()
    {
        homeSerialUiState = HomeUiState.Success(thisWeekTrendSeries)
    }

    fun getPopularMovies()
    {
        homePopularMoviesUiState = HomeUiState.Success(popularMovies)
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