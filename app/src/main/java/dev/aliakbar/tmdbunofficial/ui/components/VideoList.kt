package dev.aliakbar.tmdbunofficial.ui.components

import Carousel
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import dev.aliakbar.tmdbunofficial.data.Video
import dev.aliakbar.tmdbunofficial.ui.home.VideoDialog
import dev.aliakbar.tmdbunofficial.util.YOUTUBE_THUMBNAIL_BASE_URL
import dev.aliakbar.tmdbunofficial.util.YoutubeThumbnailSize

@Composable
fun VideoList(videos: List<Video>, onVideoClick: () -> Unit, modifier: Modifier = Modifier)
{
    val scrollState = rememberLazyListState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        state = scrollState,
        modifier = Modifier.padding(bottom = 1.dp)
    )
    {
        items(videos)
        { video ->
            VideoItem(video = video, onVideoClick)
        }
    }
    Carousel(state = scrollState, modifier = Modifier.fillMaxWidth())
}

@Composable
fun VideoItem(video: Video, onVideoClick: () -> Unit, modifier: Modifier = Modifier)
{
    Card(
        onClick = onVideoClick,
        modifier = Modifier
            .width(300.dp)
            .height(170.dp)
    )
    {
        YoutubeVideoPlayerItem(
            id = video.key
        )
    }
}

@Composable
fun YoutubeVideoPlayerItem(
    id: String,
    modifier: Modifier = Modifier
)
{
    var isVideoFullScreen by remember { mutableStateOf(false) }
    var imageLoadingState by rememberSaveable { mutableStateOf(true) }

    Box {
        SubcomposeAsyncImage(
            model = "$YOUTUBE_THUMBNAIL_BASE_URL$id${YoutubeThumbnailSize.MAX.size}",
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

        Icon(
            imageVector = Icons.Default.PlayCircleOutline,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.Center)
        )
    }

    if (isVideoFullScreen)
    {
        val configuration = LocalConfiguration.current

        when (configuration.orientation)
        {
            Configuration.ORIENTATION_LANDSCAPE ->
            {
                VideoDialog(
                    videoId = id,
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
                    videoId = id,
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
}