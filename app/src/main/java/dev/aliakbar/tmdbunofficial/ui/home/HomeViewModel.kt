package dev.aliakbar.tmdbunofficial.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aliakbar.tmdbunofficial.data.HomeRepository
import dev.aliakbar.tmdbunofficial.data.Trailer
import dev.aliakbar.tmdbunofficial.data.Trend
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

sealed interface HomeUiState
{
    data class Success(
        val todayTrendMovies: List<Trend>,
//        val thisWeekTrendMovies: List<Trend>,
        val todayTrendSeries: List<Trend>,
//        val thisWeekTrendSeries: List<Trend>,
        val popularMovies: List<Trend>,
        val popularSeries: List<Trend>,
        var todayTrendingMoviesTrailers: List<Trailer>
    ) : HomeUiState
    data object Error : HomeUiState
    data object Loading : HomeUiState
}

private val TAG: String = HomeViewModel::class.java.simpleName

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel()
{

    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init
    {
        initiateState()
    }

    fun initiateState()
    {
        viewModelScope.launch()
        {
            homeUiState = HomeUiState.Loading
            homeUiState = try
            {
                val todayTrendMovies = homeRepository.getTodayTrendingMovies()
                //val thisWeekTrendMovies = homeRepository.getThisWeekTrendingMovies()

                val todayTrendSeries = homeRepository.getTodayTrendingSeries()
                //val thisWeekTrendSeries = homeRepository.getThisWeekTrendingSeries()

                val popularMovies = homeRepository.getPopularMovies()

                val popularSeries = homeRepository.getPopularSeries()

                val todayTrendingMoviesTrailers = homeRepository.getTodayTrendingMovieTrailers()

                HomeUiState.Success(todayTrendMovies/*,thisWeekTrendMovies*/, todayTrendSeries/*,
                    thisWeekTrendSeries*/, popularMovies, popularSeries, todayTrendingMoviesTrailers)
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
}