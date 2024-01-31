@file:OptIn(ExperimentalMaterial3Api::class)

package dev.aliakbar.tmdbunofficial.ui.top

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.theme.TMDBUnofficialTheme
import kotlin.math.roundToInt

@Composable
fun TopScreen(
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToTv: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TopViewModel = hiltViewModel()
)
{
    var tabState by rememberSaveable { mutableIntStateOf(0) }
    val titles = stringArrayResource(id = R.array.content_type_option)

    Column(Modifier.fillMaxSize())
    {
        PrimaryTabRow(selectedTabIndex = tabState)
        {
            titles.forEachIndexed()
            { index, title ->
                Tab(
                    text = { Text(text = title) },
                    onClick = { tabState = index },
                    selected = (index == tabState)
                )
            }
        }
        when (tabState)
        {
            0 ->
            {
                TopList(
                    tops = viewModel.getTopRatedMovies().collectAsLazyPagingItems(),
                    onNavigate = onNavigateToMovie,
                    modifier = Modifier.padding(16.dp)
                )
            }
            1 ->
            {
                TopList(
                    tops = viewModel.getTopRatedSeries().collectAsLazyPagingItems(),
                    onNavigate = onNavigateToTv,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        /*when (val state = topMovies.loadState.refresh) { //FIRST LOAD
            is LoadState.Error   -> Text(text = "Error")
            is LoadState.Loading -> Text(text = "Loading")
            else -> { *//*TopList(tops = topMovies, navController)*//* }
        }*/

        /*when (val state = topMovies.loadState.append) { // Pagination
            is LoadState.Error   -> Text(text = "Error")
            is LoadState.Loading -> Text(text = "Loading")
            else -> { *//*TopList(tops = topMovies, navController)*//* }
        }

        TopList(tops = topMovies, navController)*/
    }
}

@Composable
fun TopList(
    tops: LazyPagingItems<Trend>,
    onNavigate: (Int) -> Unit,
    modifier: Modifier = Modifier
)
{
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        items(
            tops.itemCount,
            key = tops.itemKey { it.rank }
        )
        { index ->
            tops[index]?.let()
            {
                TopItem(
                    top = it,
                    onNavigate = { onNavigate(it.id) }
                )
            }
        }
    }
}

@Composable
fun TopItem(
    top: Trend,
    onNavigate: () -> Unit
)
{
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val backdropHeight = (screenWidth * 0.5625).roundToInt()

    val colorStops = listOf(
        Color.Black.copy(alpha = 0.0f),
        Color.Black.copy(alpha = 0.8f)
    )
    Card(
        onClick = { onNavigate() },
        modifier = Modifier
            .fillMaxWidth()
            .height(backdropHeight.dp),
        shape = CardDefaults.shape
    )
    {
        Box(
            modifier = Modifier.fillMaxSize()
        )
        {
            Image(
                url = top.backdrop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(colors = colorStops))
            )

            Text(
                text = top.rank.toString(),
                maxLines = 1,
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart),
            )

            Text(
                text = top.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 16.dp)
                    .align(Alignment.BottomStart)
            )
        }
    }
}

@Preview
@Composable
fun TopScreenPreview()
{
    TMDBUnofficialTheme()
    {
        //Top()
    }
}