@file:OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class
)

package dev.aliakbar.tmdbunofficial.ui.home

import android.content.res.Configuration
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Trailer
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.data.source.sample.trend
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicator
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.components.ScoreBar
import dev.aliakbar.tmdbunofficial.ui.main.TmdbScreen
import dev.aliakbar.tmdbunofficial.ui.theme.TMDBUnofficialTheme

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.factory),
    modifier: Modifier = Modifier
)
{
    when (val homeUiState = viewModel.homeUiState)
    {
        is HomeUiState.Loading -> CircularIndicator()
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
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                        SingleChoiceSegmentedButtonRow(modifier = Modifier.width(200.dp))
                        {
                            timeRangeOptions.forEachIndexed()
                            { index, label ->
                                SegmentedButton(
                                    modifier = Modifier.width(75.dp),
                                    selected = index == moviesSelectedTimeRangeIndex,
                                    onClick = { moviesSelectedTimeRangeIndex = index },
                                    shape = SegmentedButtonDefaults.itemShape(
                                        index = index,
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
                            onNavigateToDetails =
                            {
                                navController.navigate(TmdbScreen.MovieDetails.name + "/" + it.id.toString() + "/true")
                            },
                            viewModel = viewModel
                        )

                        1 -> TrendList(
                            trends = homeUiState.thisWeekTrendMovies,
                            onNavigateToDetails =
                            {
                                navController.navigate(TmdbScreen.MovieDetails.name + "/" + it.id.toString() + "/true")
                            },
                            viewModel = viewModel
                        )
                    }
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )

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
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                        SingleChoiceSegmentedButtonRow(modifier = Modifier.width(200.dp))
                        {
                            timeRangeOptions.forEachIndexed()
                            { index, label ->
                                SegmentedButton(
                                    modifier = Modifier.width(75.dp),
                                    selected = index == seriesSelectedTimeRangeIndex,
                                    onClick = { seriesSelectedTimeRangeIndex = index },
                                    shape = SegmentedButtonDefaults.itemShape(
                                        index = index,
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
                            onNavigateToDetails =
                            {
                                navController.navigate(TmdbScreen.MovieDetails.name + "/" + it.id.toString() + "/false")
                            },
                            viewModel = viewModel
                        )

                        1 -> TrendList(
                            trends = homeUiState.thisWeekTrendSeries,
                            onNavigateToDetails =
                            {
                                navController.navigate(TmdbScreen.MovieDetails.name + "/" + it.id.toString() + "/false")
                            },
                            viewModel = viewModel
                        )
                    }
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )

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
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                        SingleChoiceSegmentedButtonRow(modifier = Modifier.width(200.dp))
                        {
                            timeRangeOptions.forEachIndexed()
                            { index, label ->
                                SegmentedButton(
                                    modifier = Modifier.width(75.dp),
                                    selected = index == popularSelectedTypeIndex,
                                    onClick = { popularSelectedTypeIndex = index },
                                    shape = SegmentedButtonDefaults.itemShape(
                                        index = index,
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
                            onNavigateToDetails =
                            {
                                navController.navigate(TmdbScreen.MovieDetails.name + "/" + it.id.toString() + "/true")
                            },
                            viewModel = viewModel
                        )

                        1 -> TrendList(
                            trends = homeUiState.popularSeries,
                            onNavigateToDetails =
                            {
                                navController.navigate(TmdbScreen.MovieDetails.name + "/" + it.id.toString() + "/false")
                            },
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
        is HomeUiState.Error   -> Text(text = "Error")
    }
}

@Composable
fun TrendList(
    trends: List<Trend>,
    onNavigateToDetails: (Trend) -> Unit,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier)
{
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(16.dp)
    )
    {
        items(items = trends, key = { trend -> trend.id })
        { trend ->
            TrendItem(
                trend = trend,
                onNavigateToDetails = onNavigateToDetails,
                addToBookmark =
                {
                    viewModel.addToBookmark(trend)
                    //trend.isBookmark = true
                },
                removeFromBookmark =
                {
                    viewModel.removeFromBookmark(trend)
                }
            )
        }
    }
}

@Composable
fun TrendItem(
    trend: Trend,
    onNavigateToDetails: (Trend) -> Unit,
    addToBookmark: () -> Unit,
    removeFromBookmark: () -> Unit
    )
{
    Card(
        onClick = { onNavigateToDetails(trend) }
    )
    {
        Column()
        {
            Poster(trend = trend, addToBookmark = addToBookmark, removeFromBookmark = removeFromBookmark)

            Text(
                text = trend.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .width(150.dp)
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
                if (trailers[page].trend.type == "movie")
                    navController.navigate(TmdbScreen.MovieDetails.name + "/" + trailers[page].trend.id.toString() + "/true")
                else
                    navController.navigate(TmdbScreen.MovieDetails.name + "/" + trailers[page].trend.id.toString() + "/false")
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

            Image(url = trailer.trend.backdrop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .clickable { isVideoFullScreen = true }
            )

            Icon(
                imageVector = Icons.Default.PlayCircleOutline,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Center)
            )
        }

        Image(url = trailer.trend.poster,
            modifier = Modifier
                .width(150.dp)
                .height(225.dp)
                .padding(start = 16.dp, bottom = 16.dp)
                .align(Alignment.BottomStart)
                .clickable { onNavigateToDetails() })

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

@Composable
fun Poster(
    addToBookmark: () -> Unit,
    removeFromBookmark: () -> Unit,
    trend: Trend,
    modifier: Modifier = Modifier
)
{
    var isBookmark by remember { mutableStateOf(trend.isBookmark) }
    Box(
        modifier = Modifier
            .size(width = 150.dp, height = 225.dp)
    )
    {

        Image(url = trend.poster, modifier = Modifier.fillMaxSize())

        ScoreBar(
            score = trend.score,
            modifier = Modifier.
                align(Alignment.BottomStart)
        )

        IconButton(
            onClick =
            {
                if (isBookmark)
                {
                    removeFromBookmark()
                }
                else
                {
                    addToBookmark()
                }

                isBookmark = !isBookmark
            },
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.BottomEnd),
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

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewSliderItem()
{

}

//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
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

@Preview
@Composable
fun PreviewPoster()
{
    TMDBUnofficialTheme()
    {
        Poster(trend = trend, addToBookmark = {}, removeFromBookmark = {})
    }
}

@Preview
@Composable
fun PreviewScore()
{
    TMDBUnofficialTheme()
    {
        ScoreBar(score = 7.93F)
    }
}