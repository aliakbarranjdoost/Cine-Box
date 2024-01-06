@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package dev.aliakbar.tmdbunofficial.ui.top

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.components.Rank
import dev.aliakbar.tmdbunofficial.ui.main.TmdbScreen
import dev.aliakbar.tmdbunofficial.ui.theme.TMDBUnofficialTheme

@Composable
fun TopScreen(
    navController: NavHostController,
    viewModel: TopViewModel = viewModel(factory = TopViewModel.factory),
    modifier: Modifier = Modifier
)
{
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
        when (tabState)
        {
            0 -> TopList(
                tops = topMovies,
                navController = navController,
                addToBookmark = { viewModel.addToBookmark(it) },
                removeFromBookmark = { viewModel.removeFromBookmark(it) }
            )
            1 -> TopList(
                tops = topSeries,
                navController = navController,
                addToBookmark = { viewModel.addToBookmark(it) },
                removeFromBookmark = { viewModel.removeFromBookmark(it) }
            )
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
    addToBookmark: (Trend) -> Unit,
    removeFromBookmark: (Trend) -> Unit,
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
            tops[index]?.let()
            {
                TopItem(
                    top = it,
                    onNavigateToDetails = { navController.navigate(TmdbScreen.MovieDetails.name + "/" + tops[index]?.id.toString()) },
                    addToBookmark = addToBookmark,
                    removeFromBookmark = removeFromBookmark
                )
            }
        }
    }
}

@Composable
fun TopItem(top: Trend, onNavigateToDetails: () -> Unit,
            addToBookmark: (Trend) -> Unit,
            removeFromBookmark: (Trend) -> Unit,
            modifier: Modifier = Modifier)
{
    var isBookmark by remember { mutableStateOf(top.isBookmark) }

    Card(
        onClick = onNavigateToDetails,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = CardDefaults.shape
    )
    {
        Column(modifier = Modifier.fillMaxSize())
        {
            Box(modifier = Modifier
                .weight(0.76F)
                .fillMaxWidth())
            {
                Image(
                    url = top.poster, modifier = Modifier
                        .fillMaxSize()
                )
                Rank(rank = top.rank, modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopStart))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .weight(0.24F)
                    .fillMaxWidth()
            )
            {
                Text(
                    text = top.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1F)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall
                )

                IconButton(
                    onClick =
                    {
                        if (isBookmark)
                        {
                            removeFromBookmark(top)
                        }
                        else
                        {
                            addToBookmark(top)
                        }

                        isBookmark = !isBookmark
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .padding(4.dp)
                )
                {
                    Icon(
                        imageVector = if (isBookmark) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = null,
                        tint = if (isBookmark) Color.Yellow else LocalContentColor.current,
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