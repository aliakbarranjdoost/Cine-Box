package dev.aliakbar.tmdbunofficial.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Trend

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen()
{
    val viewModel: HomeViewModel = viewModel(factory = HomeViewModel.factory)
    var homeUiState = viewModel.homeUiState

    Column()
    {

        val timeRangeOptions = stringArrayResource(R.array.date_range)
        var moviesTimeSelectedIndex by remember { mutableIntStateOf(0) }
        var seriesTimeSelectedIndex by remember { mutableIntStateOf(0) }

        SingleChoiceSegmentedButtonRow(modifier = Modifier.width(200.dp))
        {
            timeRangeOptions.forEachIndexed()
            {
                index,label ->
                SegmentedButton(
                    modifier = Modifier.width(75.dp),
                    selected = index == moviesTimeSelectedIndex,
                    onClick = { moviesTimeSelectedIndex = index },
                    shape = SegmentedButtonDefaults.shape(position = index, count = timeRangeOptions.size)
                )
                {
                    Text(label)
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth(),Arrangement.Center)
        {
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { viewModel.getTodayTrendMovies() }) { Text(text = "1") }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { viewModel.getThisWeekTrendMovies() }) { Text(text = "2") }
            Spacer(modifier = Modifier.width(16.dp))
        }

        SingleChoiceSegmentedButtonRow(modifier = Modifier.width(200.dp))
        {
            timeRangeOptions.forEachIndexed()
            {
                    index,label ->
                SegmentedButton(
                    modifier = Modifier.width(75.dp),
                    selected = index == seriesTimeSelectedIndex,
                    onClick = { seriesTimeSelectedIndex = index },
                    shape = SegmentedButtonDefaults.shape(position = index, count = timeRangeOptions.size)
                )
                {
                    Text(label)
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth(),Arrangement.Center)
        {
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { viewModel.getTodayTrendSeries() }) { Text(text = "3") }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { viewModel.getThisWeekTrendSeries() }) { Text(text = "4") }
            Spacer(modifier = Modifier.width(16.dp))
        }

        when (homeUiState)
        {
            is HomeUiState.Loading -> Text(text = "Loading")
            is HomeUiState.Success -> TrendList(trends = homeUiState.trends)
            is HomeUiState.Error   -> Text(text = "Error")
        }
    }
}

@Composable
fun TrendList(trends: List<Trend>, modifier: Modifier = Modifier)
{
    LazyRow(modifier = modifier)
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