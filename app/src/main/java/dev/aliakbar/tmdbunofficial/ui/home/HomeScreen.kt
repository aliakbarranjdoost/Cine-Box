@file:OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class
)

package dev.aliakbar.tmdbunofficial.ui.home

import Carousel
import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.MediaType
import dev.aliakbar.tmdbunofficial.data.Trailer
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicator
import dev.aliakbar.tmdbunofficial.ui.components.ErrorButton
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.components.ImageLoadingAnimation
import dev.aliakbar.tmdbunofficial.ui.components.IndicatorList
import dev.aliakbar.tmdbunofficial.ui.components.ListTitleText
import dev.aliakbar.tmdbunofficial.util.YOUTUBE_THUMBNAIL_BASE_URL
import dev.aliakbar.tmdbunofficial.util.YoutubeThumbnailSize

@Composable
fun HomeScreen(
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToTv: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
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
                    onNavigateToMovie = onNavigateToMovie,
                    onNavigateToTv = onNavigateToTv
                )

                val timeRangeOptions = stringArrayResource(R.array.date_range_options)
                val typeOptions = stringArrayResource(R.array.content_type_option)

                var popularSelectedTypeIndex by remember { mutableIntStateOf(0) }

                Column(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
                )
                {
                    val paddingTop = dimensionResource(id = R.dimen.padding_top_home)
                    val paddingBottom = dimensionResource(id = R.dimen.padding_bottom_home)

                    ListTitleText(
                        title = R.string.trending_movies,
                        modifier = Modifier.padding(top = paddingTop, bottom = paddingBottom)
                    )
                    
                    TrendList(
                        trends = homeUiState.todayTrendMovies,
                        onNavigate = { onNavigateToMovie(it) }
                    )

                    ListTitleText(
                        title = R.string.trending_tvs,
                        modifier = Modifier.padding(top = paddingTop, bottom = paddingBottom)
                    )

                    TrendList(
                        trends = homeUiState.todayTrendSeries,
                        onNavigate = { onNavigateToTv(it) }
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = paddingTop, bottom = paddingBottom)
                    )
                    {
                        ListTitleText(
                            title = R.string.popular,
                            modifier = Modifier.fillMaxHeight()
                        )

                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )

                        SingleChoiceSegmentedButtonRow(modifier = Modifier.width(220.dp))
                        {
                            typeOptions.forEachIndexed()
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
                            onNavigate = { onNavigateToMovie(it) }
                        )

                        1 -> TrendList(
                            trends = homeUiState.popularSeries,
                            onNavigate = { onNavigateToTv(it) }
                        )
                    }
                }
            }
        }
        is HomeUiState.Error   -> ErrorButton { viewModel.initiateState() }
    }
}

@Composable
fun TrendList(
    trends: List<Trend>,
    onNavigate: (Int) -> Unit,
    modifier: Modifier = Modifier
)
{
    val scrollState = rememberLazyListState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_between_list_item)),
        state = scrollState,
        modifier = modifier.padding(bottom = dimensionResource(id = R.dimen.padding_from_carousel))
    )
    {
        items(items = trends, key = { trend -> trend.id })
        { trend ->
            TrendItem(
                id = trend.id,
                poster = trend.poster,
                title = trend.title,
                onNavigate = onNavigate
            )
        }
    }
    Carousel(state = scrollState, modifier = Modifier.fillMaxWidth())
}

@Composable
fun TrendItem(
    id: Int,
    poster: String,
    title: String,
    onNavigate: (Int) -> Unit,
    modifier: Modifier = Modifier
)
{
    ElevatedCard(onClick = { onNavigate(id) }, modifier = Modifier.width(170.dp))
    {
        Image(url = poster, modifier = Modifier.size(width = 170.dp, height = 255.dp))

        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleSmall,

            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        )
    }
}

@Composable
fun Slider(
    trailers: List<Trailer>,
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToTv: (Int) -> Unit,
    modifier: Modifier = Modifier
)
{
    val pagerState = rememberPagerState(pageCount = { trailers.size })

    Box()
    {
        HorizontalPager(state = pagerState)
        { page ->
            SliderItem(
                trailer = trailers[page],
                onNavigate =
                {
                    val item = trailers[page]
                    val itemId = item.trend.id
                    when (item.trend.type)
                    {
                        MediaType.MOVIE  -> onNavigateToMovie(itemId)
                        MediaType.TV     -> onNavigateToTv(itemId)
                        MediaType.PERSON -> { /*not needed here*/ }
                    }
                }
            )
        }

        IndicatorList(
            itemCount = trailers.size,
            selectedItem = pagerState.currentPage,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun SliderItem(
    trailer: Trailer,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier
)
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
                            lifecycleOwner = LocalLifecycleOwner.current,
                            modifier = Modifier.fillMaxSize(),
                            onDismissRequest = { isVideoFullScreen = false }
                        )
                    }

                    else                                ->
                    {
                        VideoDialog(
                            videoId = trailer.video.key,
                            lifecycleOwner = LocalLifecycleOwner.current,
                            onDismissRequest = { isVideoFullScreen = false },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                        )
                    }
                }
            }

            Box(
                Modifier
                    .height(230.dp)
                    .fillMaxHeight())
            {
                var imageLoadingState by rememberSaveable {
                    mutableStateOf(true)
                }
                SubcomposeAsyncImage(
                    model = "$YOUTUBE_THUMBNAIL_BASE_URL${trailer.video.key}${YoutubeThumbnailSize.MAX.size}",
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .clickable { isVideoFullScreen = true }
                )
                {
                    val state = painter.state

                    if (state is AsyncImagePainter.State.Loading)
                    {
                        ImageLoadingAnimation()
                    }
                    else
                    {
                        SubcomposeAsyncImageContent()
                        imageLoadingState = false
                    }
                }

                if (!imageLoadingState)
                {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_play_circle_outline),
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }

        Image(
            url = trailer.trend.poster,
            modifier = Modifier
                .width(170.dp)
                .height(225.dp)
                .padding(start = 16.dp)
                .align(Alignment.BottomStart)
                .clickable { onNavigate() })

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
fun YoutubeVideoPlayer(
    id: String,
    lifecycleOwner: LifecycleOwner,
    modifier: Modifier = Modifier
)
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
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
)
{
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = true),
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