package dev.aliakbar.tmdbunofficial.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
    LazyRow()
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
    Card(modifier = Modifier.padding(16.dp))
    {
        Column {
            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .data(trend.poster)
                    .build(),
                contentDescription = trend.title
            )
            Text(text = trend.title)
            Text(text = trend.score.toString())
        }
    }
}