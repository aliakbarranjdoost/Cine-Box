@file:OptIn(
    ExperimentalMaterial3Api::class
)

package dev.aliakbar.tmdbunofficial.ui.movie

import Carousel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dev.aliakbar.tmdbunofficial.data.Cast
import dev.aliakbar.tmdbunofficial.data.Crew
import dev.aliakbar.tmdbunofficial.data.Genre
import dev.aliakbar.tmdbunofficial.data.Image
import dev.aliakbar.tmdbunofficial.data.Movie
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.data.Video
import dev.aliakbar.tmdbunofficial.data.source.sample.movie
import dev.aliakbar.tmdbunofficial.ui.components.CastItem
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicator
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.components.ScoreBar

const val OVERVIEW_PREVIEW_MAX_LINE = 3

@Composable
fun MovieScreen(
    onNavigateToMovie: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MovieViewModel = viewModel(factory = MovieViewModel.factory)
)
{
    val uiState = viewModel.movieUiState

    when (uiState)
    {
        is MovieUiState.Loading -> CircularIndicator()
        is MovieUiState.Success -> MovieDetails(
            movie = uiState.movie,
            onNavigateToMovie = onNavigateToMovie,
            onNavigateBack = onNavigateBack,
            addToBookmark = { viewModel.addToBookmark(it) },
            removeFromBookmark = { viewModel.removeFromBookmark(it) }
        )

        is MovieUiState.Error   -> Text(text = "Error")
    }
}

@Composable
fun MovieDetails(
    movie: Movie,
    onNavigateToMovie: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    addToBookmark: (Movie) -> Unit,
    removeFromBookmark: (Movie) -> Unit
)
{
    val scrollState = rememberScrollState()
    var showDetails by remember { mutableStateOf(false) }
    var showPosterFullscreen by remember { mutableStateOf(false) }
    var selectedImagePath by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBar(
                title = movie.title,
                isBookmarkAlready = movie.isBookmark,
                onNavigateBack = onNavigateBack,
                addToBookmark = { addToBookmark(movie) },
                removeFromBookmark = { removeFromBookmark(movie) }
            )
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            {
                Image(
                    url = movie.backdropUrl, modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .align(Alignment.Center)
                )
                ScoreBar(
                    score = movie.voteAverage,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }

            if (!showDetails)
            {
                Text(
                    text = movie.overview,
                    maxLines = OVERVIEW_PREVIEW_MAX_LINE,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                )

                TextButton(
                    onClick = { showDetails = true },
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                )
                {
                    Text(text = "More Details")
                }
            }
            else
            {
                Column(modifier = Modifier.padding(16.dp))
                {
                    Text(text = movie.overview)
                    DetailsHeader(header = "Genres")
                    GenreList(genres = movie.genres)
                    DetailsHeader(header = "Original Language")
                    Text(text = movie.originalLanguage)
                    DetailsHeader(header = "Release Date")
                    Text(text = movie.releaseDate)
                    DetailsHeader(header = "Home Page")
                    Text(text = movie.homepage)
                    DetailsHeader(header = "Runtime")
                    Text(text = "${movie.runtime / 60}h,${movie.runtime % 60}m")
                    DetailsHeader(header = "Status")
                    Text(text = movie.status)
                    TextButton(
                        onClick = { showDetails = false },
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    )
                    {
                        Text(text = "less Details")
                    }
                }
            }


            CastList(casts = movie.casts)

            ListHeader(header = "Crew")

            CrewList(crews = movie.crews)

            if (movie.videos.isNotEmpty())
            {
                ListHeader(header = "Videos")
                VideoList(videos = movie.videos, {})
            }

            if (movie.posters.isNotEmpty())
            {
                ListHeader(header = "Posters")
                PosterList(posters = movie.posters,
                    {
                        selectedImagePath = it.fileUrl
                        showPosterFullscreen = true
                    })
            }

            if (movie.backdrops.isNotEmpty())
            {
                ListHeader(header = "Backdrops")
                BackdropList(backdrops = movie.backdrops,
                    {
                        selectedImagePath = it.fileUrl
                        showPosterFullscreen = true
                    })
            }

            if (movie.recommendations.isNotEmpty())
            {
                ListHeader(header = "Recommendations")

                RecommendationList(
                    recommendations = movie.recommendations,
                    onNavigateToMovie = onNavigateToMovie
                )
            }
        }
    }

    if (showPosterFullscreen)
    {
        ShowPosterInFullscreenDialog(posterUrl = selectedImagePath)
        {
            showPosterFullscreen = false
        }
    }
}

@Composable
fun DetailsHeader(header: String)
{
    Text(
        text = header,
        style = MaterialTheme.typography.titleMedium,
    )
}

@Composable
fun ListHeader(header: String, modifier: Modifier = Modifier)
{
    Text(
        text = header,
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier
    )
}

@Composable
fun GenreList(
    genres: List<Genre>, modifier: Modifier = Modifier
)
{
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    )
    {
        items(items = genres)
        { genre ->
            TextButton(
                onClick = { },
            )
            {
                Text(text = genre.name)
            }
        }
    }
}

@Composable
fun CastList(casts: List<Cast>, modifier: Modifier = Modifier)
{
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .background(
                MaterialTheme.colorScheme.primaryContainer,
            ),
    )
    {
        val scrollState = rememberLazyListState()

        ListHeader(header = "Cast")

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            state = scrollState,
            modifier = Modifier.padding(8.dp),
        )
        {
            items(items = casts)
            { cast ->
                CastItem(
                    name = cast.name,
                    role = cast.character,
                    pictureUrl = cast.profileUrl!!,
                    onCastClick = { }
                )
            }
        }

        Carousel(state = scrollState, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun CrewList(crews: List<Crew>, modifier: Modifier = Modifier)
{
    val scrollState = rememberLazyListState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        state = scrollState,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
    {
        items(items = crews)
        { crew ->
            CastItem(
                name = crew.name,
                role = crew.job,
                pictureUrl = crew.profileUrl!!,
                onCastClick = { }
            )
        }
    }
    Carousel(state = scrollState, modifier = Modifier.fillMaxWidth())
}

@Composable
fun VideoList(videos: List<Video>, onVideoClick: () -> Unit, modifier: Modifier = Modifier)
{
    val scrollState = rememberLazyListState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        state = scrollState,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
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
        modifier = Modifier
            .width(300.dp)
            .height(170.dp),
        onClick = onVideoClick
    )
    {
        YoutubeVideoPlayerItem(
            id = video.key,
            lifecycleOwner = LocalLifecycleOwner.current,
        )
        /*AsyncImage(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .data("https://i.ytimg.com/vi/${video.key}/hqdefault.jpg\n")
                .build(),
            placeholder = painterResource(id = R.drawable.backdrop_test),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )*/
    }
}

@Composable
fun YoutubeVideoPlayerItem(
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
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun PosterList(posters: List<Image>, onPosterClick: (Image) -> Unit, modifier: Modifier = Modifier)
{
    val scrollState = rememberLazyListState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        state = scrollState,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
    {
        items(items = posters)
        { poster ->
            PosterItem(poster = poster, onPosterClick)
        }
    }

    Carousel(state = scrollState, modifier = Modifier.fillMaxWidth())
}

@Composable
fun PosterItem(poster: Image, onPosterClick: (Image) -> Unit)
{
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(300.dp),
        onClick = { onPosterClick(poster) }
    )
    {
        Image(url = poster.fileUrl)
    }
}

@Composable
fun BackdropList(backdrops: List<Image>,onPosterClick: (Image) -> Unit, modifier: Modifier = Modifier)
{
    val scrollState = rememberLazyListState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        state = scrollState,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
    {
        items(items = backdrops)
        { backdrop ->
            BackdropItem(backdrop = backdrop, onPosterClick)
        }
    }

    Carousel(state = scrollState, modifier = Modifier.fillMaxWidth())
}

@Composable
fun BackdropItem(backdrop: Image,onPosterClick: (Image) -> Unit)
{
    Card(
        modifier = Modifier.size(width = 300.dp, height = 170.dp),
        onClick = { onPosterClick(backdrop) }
    )
    {
        Image(url = backdrop.fileUrl)
    }
}

@Composable
fun RecommendationList(
    recommendations: List<Trend>,
    onNavigateToMovie: (Int) -> Unit,
    modifier: Modifier = Modifier)
{
    val scrollState = rememberLazyListState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        state = scrollState,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
    {
        items(items = recommendations)
        { recommendation ->
            RecommendationItem(
                recommendation = recommendation,
                onNavigateToMovie = onNavigateToMovie
            )
        }
    }

    Carousel(state = scrollState, modifier = Modifier.fillMaxWidth())
}

@Composable
fun RecommendationItem(recommendation: Trend, onNavigateToMovie: (Int) -> Unit)
{
    Card(
        onClick = { onNavigateToMovie(recommendation.id) },
        modifier = Modifier.size(width = 200.dp, height = 325.dp))
    {
        Column()
        {
            Image(url = recommendation.poster, modifier = Modifier.height(300.dp))
            Text(
                text = recommendation.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun TopBar(
    title: String,
    isBookmarkAlready: Boolean,
    onNavigateBack: () -> Unit,
    addToBookmark: () -> Unit,
    removeFromBookmark: () -> Unit)
{
    var isBookmark by remember { mutableStateOf(isBookmarkAlready) }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Localized description"
                )
            }

            IconButton(onClick =
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
            }
            ) {
                Icon(
                    imageVector = if (isBookmark) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    tint = if (isBookmark) Color.Yellow else LocalContentColor.current,
                    contentDescription = null
                )
            }
        },
    )
}

@Composable
fun ShowPosterInFullscreenDialog(
    posterUrl: String,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit
)
{
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = { onDismissRequest() })
    {
        Image(url = posterUrl)
    }
}

@Preview
@Composable
fun MovieDetailsPreview()
{
    MovieDetails(movie = movie, {}, {}, {}, {})
}
