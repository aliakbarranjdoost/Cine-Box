@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package dev.aliakbar.tmdbunofficial.ui.top

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.ui.main.TmdbScreen
import dev.aliakbar.tmdbunofficial.ui.theme.TMDBUnofficialTheme

@Composable
fun TopScreen(
    navController: NavHostController,
    viewModel: TopViewModel = viewModel(factory = TopViewModel.factory),
    modifier: Modifier = Modifier
)
{
    val uiState = viewModel.topUiState
    val topMovies = viewModel.getTopRatedMovies().collectAsLazyPagingItems()
    val topSeries = viewModel.getTopRatedSeries().collectAsLazyPagingItems()

    var tabState by remember { mutableIntStateOf(0) }
    val titles = stringArrayResource(id = R.array.content_type_option)

    Column()
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
        /*when (uiState)
        {
            is TopUiState.Loading -> Text(text = "Loading")
            is TopUiState.Success -> TopList(tops = uiState.topRatedMovies, navController)
            is TopUiState.Error   -> Text(text = "Error")
        }*/

        when (tabState)
        {
            0 -> TopList(tops = topMovies, navController)
            1 -> TopList(tops = topSeries, navController)
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
    navController: NavHostController,
    modifier: Modifier = Modifier
)
{
    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        items(
            tops.itemCount,
            key = tops.itemKey { it.rank }
        )
        { index ->
            tops[index]?.let {
                TopItem(
                    top = it,
                    { navController.navigate(TmdbScreen.MovieDetails.name + "/" + tops[index]?.id.toString()) }
                )
            }
        }
    }
}

@Composable
fun TopItem(top: Trend, onNavigateToDetails: () -> Unit, modifier: Modifier = Modifier)
{
    Card(
        onClick = onNavigateToDetails,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = CardDefaults.shape
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
        )
        {
            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .data(top.poster)
                    .build(),
                placeholder = painterResource(id = R.drawable.backdrop_test),
                contentDescription = top.title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .weight(0.76F)
                    .fillMaxWidth()
            )

            Text(
                text = top.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(0.12F)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.headlineSmall
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .weight(0.12F)
                    .fillMaxWidth()
            )
            {
                Text(
                    text = top.rank.toString(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f),
                    style = MaterialTheme.typography.titleSmall
                )

                IconButton(
                    onClick = { /* doSomething() */ },
                    modifier = Modifier
                        .size(48.dp)
                        .padding(4.dp)
                )
                {
                    Icon(
                        imageVector = Icons.Filled.BookmarkBorder,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TopScreenPreview()
{
    TMDBUnofficialTheme()
    {
        //TopScreen()
    }
}