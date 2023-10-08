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
import dev.aliakbar.tmdbunofficial.data.HomeRepository
import dev.aliakbar.tmdbunofficial.data.Trailer
import dev.aliakbar.tmdbunofficial.data.Video
import dev.aliakbar.tmdbunofficial.data.toExternal
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface HomeUiState
{
    data class Success(
        val todayTrendMovies: List<Trend>,
        val thisWeekTrendMovies: List<Trend>,
        val todayTrendSeries: List<Trend>,
        val thisWeekTrendSeries: List<Trend>,
        val popularMovies: List<Trend>,
        val popularSeries: List<Trend>,
        var todayTrendingMoviesTrailers: List<Trailer>
    ) : HomeUiState
    data object Error : HomeUiState
    data object Loading : HomeUiState
}

private val TAG: String = HomeViewModel::class.java.simpleName

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel()
{

    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init
    {
        viewModelScope.launch()
        {
            homeUiState = HomeUiState.Loading
            homeUiState = try
            {
                val todayTrendMovies = homeRepository.getTodayTrendingMovies().toExternal()
                val thisWeekTrendMovies = homeRepository.getThisWeekTrendingMovies().toExternal()

                val todayTrendSeries = homeRepository.getTodayTrendingSeries().toExternal()
                val thisWeekTrendSeries = homeRepository.getThisWeekTrendingSeries().toExternal()

                val popularMovies = homeRepository.getPopularMovies().toExternal()

                val popularSeries = homeRepository.getPopularSeries().toExternal()

                val todayTrendingMoviesTrailers = homeRepository.getTodayTrendingMovieTrailers()

                HomeUiState.Success(todayTrendMovies,thisWeekTrendMovies, todayTrendSeries,
                    thisWeekTrendSeries, popularMovies, popularSeries, todayTrendingMoviesTrailers)
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

    fun addToBookmark(trend: Trend)
    {
        viewModelScope.launch()
        {
            homeRepository.addTrendToBookmark(trend)
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
                val trendingRepository = application.container.homeRepository
                HomeViewModel(trendingRepository)
            }
        }
    }
}