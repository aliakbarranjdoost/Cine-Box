@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class, ExperimentalFoundationApi::class
)

package dev.aliakbar.tmdbunofficial.ui.home

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.data.Video
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

    when(homeUiState)
    {
        is HomeUiState.Loading -> Text(text = "Loading")
        is HomeUiState.Success ->
        {
            Slider(trailers = homeUiState.todayTrendingMoviesTrailers)
        }
        is HomeUiState.Error   -> Text(text = "Error")
    }
    
    /*val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState))
    {
        val timeRangeOptions = stringArrayResource(R.array.date_range_options)
        var moviesSelectedTimeRangeIndex by remember { mutableIntStateOf(0) }
        var seriesSelectedTimeRangeIndex by remember { mutableIntStateOf(0) }
        var popularSelectedTypeIndex by remember { mutableIntStateOf(0) }

        //Slider(trailers = viewModel.todayTrendingMoviesTrailers)

        Row(modifier = Modifier.fillMaxWidth())
        {
            Text(text = "Trending Movies")
            Spacer(modifier = Modifier.width(16.dp))
            SingleChoiceSegmentedButtonRow(modifier = Modifier.width(200.dp))
            {
                timeRangeOptions.forEachIndexed()
                { index, label ->
                    SegmentedButton(
                        modifier = Modifier.width(75.dp),
                        selected = index == moviesSelectedTimeRangeIndex,
                        onClick =
                        {
                            moviesSelectedTimeRangeIndex = index
                            when (moviesSelectedTimeRangeIndex)
                            {
                                0 -> viewModel.getTodayTrendMovies()
                                1 -> viewModel.getThisWeekTrendMovies()
                            }
                        },
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

        when (homeUiState)
        {
            is HomeUiState.Loading -> Text(text = "Loading")
            is HomeUiState.Success -> TrendList(
                trends = homeUiState.trends,
                navController = navController
            )
            is HomeUiState.Error   -> Text(text = "Error")
        }

        Row(modifier = Modifier.fillMaxWidth())
        {
            Text(text = "Trending Series")
            Spacer(modifier = Modifier.width(16.dp))
            SingleChoiceSegmentedButtonRow(modifier = Modifier.width(200.dp))
            {
                timeRangeOptions.forEachIndexed()
                { index, label ->
                    SegmentedButton(
                        modifier = Modifier.width(75.dp),
                        selected = index == seriesSelectedTimeRangeIndex,
                        onClick =
                        {
                            seriesSelectedTimeRangeIndex = index
                            when (seriesSelectedTimeRangeIndex)
                            {
                                0 -> viewModel.getTodayTrendSeries()
                                1 -> viewModel.getThisWeekTrendSeries()
                            }
                        },
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

        when (homeSerialUiState)
        {
            is HomeUiState.Loading -> Text(text = "Loading")
            is HomeUiState.Success -> TrendList(trends = homeSerialUiState.trends,
                navController = navController
            )
            is HomeUiState.Error   -> Text(text = "Error")
        }

        Row(modifier = Modifier.fillMaxWidth())
        {
            Text(text = "Popular Movies")
            Spacer(modifier = Modifier.width(16.dp))
            SingleChoiceSegmentedButtonRow(modifier = Modifier.width(200.dp))
            {
                timeRangeOptions.forEachIndexed()
                { index, label ->
                    SegmentedButton(
                        modifier = Modifier.width(75.dp),
                        selected = index == popularSelectedTypeIndex,
                        onClick =
                        {
                            popularSelectedTypeIndex = index
                            when (popularSelectedTypeIndex)
                            {
                                0 -> viewModel.getPopularMovies()
                                1 -> viewModel.getPopularSeries()
                            }
                        },
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

        when (homePopularMoviesUiState)
        {
            is HomeUiState.Loading -> Text(text = "Loading")
            is HomeUiState.Success -> TrendList(
                trends = homePopularMoviesUiState.trends,
                navController = navController
            )
            is HomeUiState.Error   -> Text(text = "Error")
        }*/
    //}
}

@Composable
fun TrendList(trends: List<Trend>,navController: NavHostController, modifier: Modifier = Modifier)
{
    LazyRow(modifier = modifier)
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
fun TrendItem(trend: Trend,onNavigateToDetails: () -> Unit)
{
    Card(modifier = Modifier.padding(16.dp), onClick = onNavigateToDetails)
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

@Composable
fun Slider(trailers: List<Pair<Video,Trend>>)
{
    val pagerState = rememberPagerState(pageCount = {
        trailers.size
    })

    HorizontalPager(state = pagerState)
    { page ->
        SliderItem(trailers[page])
    }
}

@Composable
fun SliderItem(trailer: Pair<Video,Trend>)
{
    var isVideoFullScreen by remember { mutableStateOf(false) }
    //val shipZIndex = if (submarineMode) -1f else 1f

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(335.dp)
        /*.clickable()
        {
            isVideoFullScreen = true
        }*/
    )
    {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.TopStart)

        )
        {
            /*if (isVideoFullScreen)
            {
                VideoDialog(videoId = trailer.first.id)
                {
                    isVideoFullScreen = false
                }
            }
            else
            {
                YoutubeVideoPlayer(id = trailer.first.key, LocalLifecycleOwner.current)
            }*/

            Icon(
                imageVector = Icons.Default.PlayCircleOutline,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Center)
            )

            Image(
                painter = painterResource(id = R.drawable.backdrop_test),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxWidth().height(230.dp)
            )

        }

        AsyncImage(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .data(trailer.second.poster)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(150.dp)
                .height(225.dp)
                .padding(start = 16.dp, bottom = 16.dp)
                .align(Alignment.BottomStart)
        )

        Text(
            text = trailer.second.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            modifier = Modifier
                .padding(start = 182.dp, bottom = 75.dp)
                .align(Alignment.BottomStart)
        )

        Text(
            text = trailer.first.name,
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
    AndroidView(factory =
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
    })
}

@Composable
fun VideoDialog(videoId: String, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() })
    {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            YoutubeVideoPlayer(id = videoId, lifecycleOwner = LocalLifecycleOwner.current)
        }
    }
}
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewSliderItem()
{
    TMDBUnofficialTheme()
    {
        //Slider(trailers = videos)
    }
}