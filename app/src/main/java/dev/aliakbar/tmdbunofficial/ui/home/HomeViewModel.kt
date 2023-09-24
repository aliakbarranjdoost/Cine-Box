package dev.aliakbar.tmdbunofficial.ui.home

import android.util.Log
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
import dev.aliakbar.tmdbunofficial.data.HomeRepository
import dev.aliakbar.tmdbunofficial.data.Video
import dev.aliakbar.tmdbunofficial.data.toExternal
import dev.aliakbar.tmdbunofficial.ui.details.DetailsViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface HomeUiState
{
    data class Success(val trends: List<Trend>) : HomeUiState
    data object Error : HomeUiState
    data object Loading : HomeUiState
}

private val TAG: String = HomeViewModel::class.java.simpleName

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel()
{
    private lateinit var todayTrendMovies: List<Trend>
    private lateinit var thisWeekTrendMovies: List<Trend>
    private lateinit var todayTrendSeries: List<Trend>
    private lateinit var thisWeekTrendSeries: List<Trend>
    private lateinit var popularMovies: List<Trend>
    private lateinit var popularSeries: List<Trend>
    private lateinit var todayTrendingMoviesTrailers: List<Video>

    var homeMovieUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set
    var homeSerialUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set
    var homePopularMoviesUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set
    var homePopularSeriesUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)

    init
    {
        viewModelScope.launch()
        {
            homeMovieUiState = HomeUiState.Loading
            homeMovieUiState = try
            {
                todayTrendMovies = homeRepository.getTodayTrendingMovies().toExternal()
                thisWeekTrendMovies = homeRepository.getThisWeekTrendingMovies().toExternal()
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
                todayTrendSeries = homeRepository.getTodayTrendingSeries().toExternal()
                thisWeekTrendSeries = homeRepository.getThisWeekTrendingSeries().toExternal()
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
                popularMovies = homeRepository.getPopularMovies().toExternal()
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

            homePopularSeriesUiState = HomeUiState.Loading
            homePopularSeriesUiState = try
            {
                popularSeries = homeRepository.getPopularSeries().toExternal()
                HomeUiState.Success(popularSeries)
            }
            catch (e: IOException)
            {
                HomeUiState.Error
            }
            catch (e: HttpException)
            {
                HomeUiState.Error
            }

            todayTrendingMoviesTrailers = homeRepository.getTodayTrendingMovieTrailers()
            Log.d(TAG, todayTrendingMoviesTrailers.toString())
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

    fun getPopularSeries()
    {
        homePopularMoviesUiState = HomeUiState.Success(popularSeries)
    }

    companion object
    {
        val factory: ViewModelProvider.Factory = viewModelFactory()
        {
            initializer()
            {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TmdbUnofficialApplication)
                val trendingRepository = application.container.homeRepository
                HomeViewModel(trendingRepository)
            }
        }
    }
}