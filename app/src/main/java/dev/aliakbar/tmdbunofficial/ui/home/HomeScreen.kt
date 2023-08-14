package dev.aliakbar.tmdbunofficial.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import dev.aliakbar.tmdbunofficial.data.Trend

@Composable
fun HomeScreen()
{
    val viewModel: HomeViewModel = viewModel(factory = HomeViewModel.factory)
    var homeUiState = viewModel.homeUiState

    when (homeUiState)
    {
        is HomeUiState.Loading -> Text(text = "Loading")
        is HomeUiState.Success -> TrendList(trends = homeUiState.trends)
        is HomeUiState.Error   -> Text(text = "Error")
    }
}

@Composable
fun TrendList(trends: List<Trend>)
{
    LazyColumn()
    {
        items(items = trends, key = { trend -> trend.id })
        { trend ->
            TrendItem(trend = trend)
        }
    }
}

@Composable
fun TrendItem(trend: Trend)
{
    Column {
        AsyncImage(model = trend.poster, contentDescription = trend.title)
        Text(text = trend.title)
        Text(text = trend.score.toString())
    }
}