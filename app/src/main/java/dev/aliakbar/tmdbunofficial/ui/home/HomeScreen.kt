@file:OptIn(
    ExperimentalFoundationApi::class
)

package dev.aliakbar.tmdbunofficial.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Trailer
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.data.source.sample.trailers
import dev.aliakbar.tmdbunofficial.ui.main.TmdbScreen
import dev.aliakbar.tmdbunofficial.ui.theme.TMDBUnofficialTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.factory),
    modifier: Modifier = Modifier
)
{
    val homeUiState = viewModel.homeUiState

    when (homeUiState)
    {
        is HomeUiState.Loading -> Text(text = "Loading")
        is HomeUiState.Success ->
        {

            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            )
            {
                Slider(
                    trailers = homeUiState.todayTrendingMoviesTrailers,
                    navController = navController
                )

                val timeRangeOptions = stringArrayResource(R.array.date_range_options)
                var moviesSelectedTimeRangeIndex by remember { mutableIntStateOf(0) }
                var seriesSelectedTimeRangeIndex by remember { mutableIntStateOf(0) }
                var popularSelectedTypeIndex by remember { mutableIntStateOf(0) }

                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
                {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    )
                    {
                        Text(text = "Trending Movies")
                        Spacer(modifier = Modifier.weight(1f).fillMaxHeight())
                        SingleChoiceSegmentedButtonRow(modifier = Modifier.width(200.dp))
                        {
                            timeRangeOptions.forEachIndexed()
                            { index, label ->
                                SegmentedButton(
                                    modifier = Modifier.width(75.dp),
                                    selected = index == moviesSelectedTimeRangeIndex,
                                    onClick = { moviesSelectedTimeRangeIndex = index },
                                    shape = SegmentedButtonDefaults.shape(
                                        position = index,
                                        count = timeRangeOptions.size
                                    )
                                )
                                {
                                    Text(label)
                                }
                            }
                        }
                    }

                    when (moviesSelectedTimeRangeIndex)
                    {
                        0 -> TrendList(
                            trends = homeUiState.todayTrendMovies,
                            navController = navController
                        )

                        1 -> TrendList(
                            trends = homeUiState.thisWeekTrendMovies,
                            navController = navController
                        )
                    }
                }

                Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))

                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
                {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    )
                    {
                        Text(text = "Trending Series")
                        Spacer(modifier = Modifier.weight(1f).fillMaxHeight())
                        SingleChoiceSegmentedButtonRow(modifier = Modifier.width(200.dp))
                        {
                            timeRangeOptions.forEachIndexed()
                            { index, label ->
                                SegmentedButton(
                                    modifier = Modifier.width(75.dp),
                                    selected = index == seriesSelectedTimeRangeIndex,
                                    onClick = { seriesSelectedTimeRangeIndex = index },
                                    shape = SegmentedButtonDefaults.shape(
                                        position = index,
                                        count = timeRangeOptions.size
                                    )
                                )
                                {
                                    Text(label)
                                }
                            }
                        }
                    }

                    when (seriesSelectedTimeRangeIndex)
                    {
                        0 -> TrendList(
                            trends = homeUiState.todayTrendSeries,
                            navController = navController
                        )

                        1 -> TrendList(
                            trends = homeUiState.thisWeekTrendSeries,
                            navController = navController
                        )
                    }
                }

                Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))

                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
                {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    )
                    {
                        Text(text = "Popular")
                        Spacer(modifier = Modifier.weight(1f).fillMaxHeight())
                        SingleChoiceSegmentedButtonRow(modifier = Modifier.width(200.dp))
                        {
                            timeRangeOptions.forEachIndexed()
                            { index, label ->
                                SegmentedButton(
                                    modifier = Modifier.width(75.dp),
                                    selected = index == popularSelectedTypeIndex,
                                    onClick = { popularSelectedTypeIndex = index },
                                    shape = SegmentedButtonDefaults.shape(
                                        position = index,
                                        count = timeRangeOptions.size
                                    )
                                )
                                {
                                    Text(label)
                                }
                            }
                        }
                    }

                    when (popularSelectedTypeIndex)
                    {
                        0 -> TrendList(
                            trends = homeUiState.popularMovies,
                            navController = navController
                        )

                        1 -> TrendList(
                            trends = homeUiState.popularSeries,
                            navController = navController
                        )
                    }
                }
            }
        }

        is HomeUiState.Error   -> Text(text = "Error")
    }


}

@Composable
fun TrendList(trends: List<Trend>, navController: NavHostController, modifier: Modifier = Modifier)
{
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(16.dp)
    )
    {
        items(items = trends, key = { trend -> trend.id })
        { trend ->
            TrendItem(trend = trend, onNavigateToDetails =
            {
                navController.navigate(TmdbScreen.MovieDetails.name + "/" + trend.id.toString())
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendItem(trend: Trend, onNavigateToDetails: () -> Unit)
{
    Card(
        onClick = onNavigateToDetails
    )
    {
        Column()
        {
            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .data(trend.poster)
                    .build(),
                contentDescription = trend.title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(width = 150.dp, height = 225.dp)
            )
            Text(
                text = trend.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .width(150.dp)
            )
            Text(
                text = trend.score.toString()
            )
        }
    }
}

@Composable
fun Slider(trailers: List<Trailer>, navController: NavHostController)
{
    val pagerState = rememberPagerState(pageCount = {
        trailers.size
    })

    HorizontalPager(state = pagerState)
    { page ->
        SliderItem(
            trailer = trailers[page],
            onNavigateToDetails =
            {
                navController.navigate(TmdbScreen.MovieDetails.name + "/" + trailers[page].trend.id.toString())
            })
    }
}

@Composable
fun SliderItem(trailer: Trailer, onNavigateToDetails: () -> Unit)
{
    var isVideoFullScreen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(335.dp)
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.TopStart)
        )
        {
            if (isVideoFullScreen)
            {
                val configuration = LocalConfiguration.current

                when (configuration.orientation)
                {
                    Configuration.ORIENTATION_LANDSCAPE ->
                    {
                        VideoDialog(
                            videoId = trailer.video.key,
                            LocalLifecycleOwner.current,
                            modifier = Modifier.fillMaxSize()
                        )
                        {
                            isVideoFullScreen = false
                        }
                    }

                    else                                ->
                    {
                        VideoDialog(
                            videoId = trailer.video.key,
                            LocalLifecycleOwner.current,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                        )
                        {
                            isVideoFullScreen = false
                        }
                    }
                }

            }

            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .data(trailer.trend.backdrop)
                    .placeholder(drawableResId = R.drawable.backdrop_test)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .clickable()
                    {
                        isVideoFullScreen = true
                    }
            )

            Icon(
                imageVector = Icons.Default.PlayCircleOutline,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Center)
            )
        }

        AsyncImage(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .data(trailer.trend.poster)
                .placeholder(drawableResId = R.drawable.poster_test)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(150.dp)
                .height(225.dp)
                .padding(start = 16.dp, bottom = 16.dp)
                .align(Alignment.BottomStart)
                .clickable { onNavigateToDetails() }
        )

        Text(
            text = trailer.trend.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            modifier = Modifier
                .padding(start = 182.dp, bottom = 75.dp)
                .align(Alignment.BottomStart)
        )

        Text(
            text = trailer.video.name,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1,
            modifier = Modifier
                .padding(start = 182.dp, bottom = 50.dp)
                .align(Alignment.BottomStart)
        )
    }
}

@Composable
fun YoutubeVideoPlayer(id: String, lifecycleOwner: LifecycleOwner, modifier: Modifier = Modifier)
{
    AndroidView(
        factory =
        { context ->
            YouTubePlayerView(context = context).apply()
            {
                lifecycleOwner.lifecycle.addObserver(this)

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener()
                {
                    override fun onReady(youTubePlayer: YouTubePlayer)
                    {
                        youTubePlayer.setLoop(true)
                        youTubePlayer.cueVideo(id, 0F)
                    }
                })
            }
        }, modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun VideoDialog(
    videoId: String,
    lifecycleOwner: LifecycleOwner,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit
)
{
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = { onDismissRequest() })
    {
        Card(
            modifier = modifier,
        )
        {
            YoutubeVideoPlayer(id = videoId, lifecycleOwner = lifecycleOwner)
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewSliderItem()
{
    TMDBUnofficialTheme()
    {
        //Slider(trailers = trailers,  )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewVideoDialog()
{
    TMDBUnofficialTheme()
    {
        VideoDialog(videoId = "", LocalLifecycleOwner.current)
        {

        }
    }
}