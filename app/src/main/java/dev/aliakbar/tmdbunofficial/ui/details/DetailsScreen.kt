@file:OptIn(
    ExperimentalMaterial3Api::class
)

package dev.aliakbar.tmdbunofficial.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Cast
import dev.aliakbar.tmdbunofficial.data.Crew
import dev.aliakbar.tmdbunofficial.data.Genre
import dev.aliakbar.tmdbunofficial.data.Image
import dev.aliakbar.tmdbunofficial.data.Movie
import dev.aliakbar.tmdbunofficial.data.Season
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.data.Tv
import dev.aliakbar.tmdbunofficial.data.Video
import dev.aliakbar.tmdbunofficial.data.source.sample.movie
import dev.aliakbar.tmdbunofficial.ui.components.CastItem
import dev.aliakbar.tmdbunofficial.ui.components.ScoreBar
import dev.aliakbar.tmdbunofficial.ui.main.TmdbScreen

const val OVERVIEW_PREVIEW_MAX_LINE = 3

@Composable
fun DetailsScreen(
    navController: NavHostController,
    viewModel: DetailsViewModel = viewModel(factory = DetailsViewModel.factory)
)
{
    val uiState = viewModel.detailsUiState

    when (uiState)
    {
        is DetailsUiState.Loading -> Text(text = "Loading")
        is DetailsUiState.SuccessMovie -> MovieDetails(movie = uiState.movie, onBookmarkClick = { })
        is DetailsUiState.SuccessTv ->
            TvDetails(
            tv = uiState.tv,
            onBookmarkClick = {},
            onSeasonClick = { navController.navigate(TmdbScreen.EpisodeList.name + "/" + uiState.tv.id + "/" + it)}
            )
        is DetailsUiState.Error   -> Text(text = "Error")
    }
}

@Composable
fun MovieDetails(movie: Movie, onBookmarkClick: () -> Unit)
{
    val scrollState = rememberScrollState()
    var showDetails by remember { mutableStateOf(false) }
    var showPosterFullscreen by remember { mutableStateOf(false) }
    var selectedImagePath by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopBar(title = movie.title, onBookmarkClick) }
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
                AsyncImage(
                    model = ImageRequest
                        .Builder(context = LocalContext.current)
                        .data(movie.backdropUrl)
                        .build(),
                    placeholder = painterResource(id = R.drawable.backdrop_test),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
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

            ListHeader(header = "Cast")

            CastList(casts = movie.casts)

            ListHeader(header = "Crew")

            CrewList(crews = movie.crews)

            ListHeader(header = "Videos")

            VideoList(videos = movie.videos, {})

            ListHeader(header = "Posters")

            PosterList(posters = movie.posters,
                {
                    selectedImagePath = it.fileUrl
                    showPosterFullscreen = true
                })

            ListHeader(header = "Backdrops")

            BackdropList(backdrops = movie.backdrops,
                {
                    selectedImagePath = it.fileUrl
                    showPosterFullscreen = true
                })

            if (movie.recommendations.isNotEmpty())
            {
                ListHeader(header = "Recommendations")

                RecommendationList(recommendations = movie.recommendations)
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
fun TvDetails(
    tv: Tv,
    //seasonDetailsUiState: SeasonDetailsUiState,
    onBookmarkClick: () -> Unit,
    onSeasonClick: (Int) -> Unit,
    modifier: Modifier = Modifier
)
{
    val scrollState = rememberScrollState()
    var showDetails by remember { mutableStateOf(false) }
    var showPosterFullscreen by remember { mutableStateOf(false) }
    var selectedImagePath by remember { mutableStateOf("") }
    var showSeasonDetails by remember { mutableStateOf(false) }
    //var selectedSeasonId by remember {
    //    mutableStateOf()
    //}

    Scaffold(
        topBar = { TopBar(title = tv.name, onBookmarkClick) }
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
                AsyncImage(
                    model = ImageRequest
                        .Builder(context = LocalContext.current)
                        .data(tv.backdropUrl)
                        .build(),
                    placeholder = painterResource(id = R.drawable.backdrop_test),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .align(Alignment.Center)
                )
                ScoreBar(
                    score = tv.voteAverage,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }

            if (!showDetails)
            {
                Text(
                    text = tv.overview,
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
                    Text(text = tv.overview)
                    DetailsHeader(header = "Genres")
                    GenreList(genres = tv.genres)
                    DetailsHeader(header = "Original Language")
                    Text(text = tv.originalLanguage)
                    DetailsHeader(header = "Release Date")
                    Text(text = tv.firstAirDate)
                    DetailsHeader(header = "Home Page")
                    Text(text = tv.homepage)
                    DetailsHeader(header = "Seasons")
                    Text(text = tv.numberOfSeasons.toString())
                    DetailsHeader(header = "Episodes")
                    Text(text = tv.numberOfEpisodes.toString())
                    DetailsHeader(header = "Status")
                    Text(text = tv.status)
                    DetailsHeader(header = "Type")
                    Text(text = tv.type)
                    TextButton(
                        onClick = { showDetails = false },
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    )
                    {
                        Text(text = "less Details")
                    }
                }
            }

            ListHeader(header = "Seasons")

            SeasonList(seasons = tv.seasons, onSeasonClick)

            ListHeader(header = "Cast")

            CastList(casts = tv.casts)

            ListHeader(header = "Crew")

            CrewList(crews = tv.crews)

            ListHeader(header = "Videos")

            VideoList(videos = tv.videos, {})

            ListHeader(header = "Posters")

            PosterList(posters = tv.posters,
                {
                    selectedImagePath = it.fileUrl
                    showPosterFullscreen = true
                })

            ListHeader(header = "Backdrops")

            BackdropList(backdrops = tv.backdrops,
                {
                    selectedImagePath = it.fileUrl
                    showPosterFullscreen = true
                })

            if (tv.recommendations.isNotEmpty())
            {
                ListHeader(header = "Recommendations")

                RecommendationList(recommendations = tv.recommendations)
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
fun ListHeader(header: String)
{
    Text(
        text = header,
        style = MaterialTheme.typography.headlineSmall,
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
            //GenreItem(genre.name)
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
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
    {
        items(items = casts)
        { cast ->
            CastItem(
                name = cast.name,
                role = cast.character,
                // TODO: Change to place holder later
                pictureUrl = cast.profileUrl!!,
                onCastClick = { }
            )
        }
    }
}

@Composable
fun CrewList(crews: List<Crew>, modifier: Modifier = Modifier)
{
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
    {
        items(items = crews)
        { crew ->
            CastItem(
                name = crew.name,
                role = crew.job,
                // TODO: Change to place holder later
                pictureUrl = crew.profileUrl!!,
                onCastClick = { }
            )
        }
    }
}

@Composable
fun VideoList(videos: List<Video>, onVideoClick: () -> Unit, modifier: Modifier = Modifier)
{
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
    {
        items(videos)
        { video ->
            VideoItem(video = video, onVideoClick)
        }
    }
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
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
    {
        items(items = posters)
        { poster ->
            PosterItem(poster = poster, onPosterClick)
        }
    }
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
        AsyncImage(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .data(poster.fileUrl)
                .build(),
            placeholder = painterResource(id = R.drawable.poster_test),
            contentDescription = null
        )
    }
}

@Composable
fun BackdropList(backdrops: List<Image>,onPosterClick: (Image) -> Unit, modifier: Modifier = Modifier)
{
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
    {
        items(items = backdrops)
        { backdrop ->
            BackdropItem(backdrop = backdrop, onPosterClick)
        }
    }
}

@Composable
fun BackdropItem(backdrop: Image,onPosterClick: (Image) -> Unit)
{
    Card(
        modifier = Modifier.size(width = 300.dp, height = 170.dp),
        onClick = { onPosterClick(backdrop) }
    )
    {
        AsyncImage(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .data(backdrop.fileUrl)
                .build(),
            placeholder = painterResource(id = R.drawable.backdrop_test),
            contentDescription = null
        )
    }
}

@Composable
fun RecommendationList(recommendations: List<Trend>, modifier: Modifier = Modifier)
{
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
    {
        items(items = recommendations)
        { recommendation ->
            RecommendationItem(recommendation = recommendation)
        }
    }
}

@Composable
fun RecommendationItem(recommendation: Trend)
{
    Card(modifier = Modifier.size(width = 200.dp, height = 325.dp))
    {
        Column()
        {
            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .data(recommendation.poster)
                    .build(),
                placeholder = painterResource(id = R.drawable.poster_test),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.height(300.dp)
            )

            Text(
                text = recommendation.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun TopBar(title: String, onBookmarkClick: () -> Unit)
{
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
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

            IconButton(onClick = onBookmarkClick) {
                Icon(
                    imageVector = Icons.Filled.BookmarkBorder,
                    contentDescription = "Localized description"
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
        AsyncImage(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .data(posterUrl)
                .build(),
            placeholder = painterResource(id = R.drawable.poster_test),
            contentScale = ContentScale.Fit,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun SeasonList(seasons: List<Season>, onSeasonClick: (Int) -> Unit, modifier: Modifier = Modifier)
{
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
    {
        items(items = seasons)
        { season ->
            SeasonItem( season, onSeasonClick)
        }
    }
}

@Composable
fun SeasonItem(season: Season, onSeasonClick: (Int) -> Unit)
{
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(400.dp),
        onClick = { onSeasonClick(season.seasonNumber) }
    )
    {
        Column {
            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .data(season.posterPath)
                    .build(),
                placeholder = painterResource(id = R.drawable.poster_test),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                modifier = Modifier
                    .width(200.dp)
                    .height(300.dp)
            )

            Text(text = season.name)

            Row {
                Text(text = season.airDate)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = season.episodeCount.toString())
            }
        }
    }
}
@Preview
@Composable
fun MovieDetailsPreview()
{
    MovieDetails(movie = movie) { }
}
