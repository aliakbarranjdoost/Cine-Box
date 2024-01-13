@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package dev.aliakbar.tmdbunofficial.ui.top

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.components.Rank
import dev.aliakbar.tmdbunofficial.ui.theme.TMDBUnofficialTheme
import kotlin.math.roundToInt

@Composable
fun TopScreen(
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToTv: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TopViewModel = viewModel(factory = TopViewModel.factory)
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
                    addToBookmark = { viewModel.addToBookmark(it) },
                    removeFromBookmark = { viewModel.removeFromBookmark(it) }
                )
            }
            1 ->
            {
                TopList(
                    tops = viewModel.getTopRatedSeries().collectAsLazyPagingItems(),
                    onNavigate = onNavigateToTv,
                    addToBookmark = { viewModel.addToBookmark(it) },
                    removeFromBookmark = { viewModel.removeFromBookmark(it) }
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
                    onNavigateToDetails = { onNavigate(it.id) },
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
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val backdropHeight = (screenWidth * 0.5625).roundToInt()

    Card(
        onClick = onNavigateToDetails,
        modifier = Modifier
            .fillMaxWidth()
            .height(backdropHeight.dp + 46.dp),
        shape = CardDefaults.shape
    )
    {
        Column(modifier = Modifier.fillMaxSize())
        {
            Box(modifier = Modifier
                .height(backdropHeight.dp)
                .fillMaxWidth())
            {
                Image(
                    url = top.poster, modifier = Modifier
                        .fillMaxSize()
                )
                Rank(rank = top.rank, modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopStart))
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
                        .align(Alignment.BottomEnd)
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

            Text(
                text = top.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.headlineSmall
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