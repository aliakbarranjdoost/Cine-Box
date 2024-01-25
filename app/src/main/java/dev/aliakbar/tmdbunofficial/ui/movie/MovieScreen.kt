package dev.aliakbar.tmdbunofficial.ui.movie

import Carousel
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Cast
import dev.aliakbar.tmdbunofficial.data.Crew
import dev.aliakbar.tmdbunofficial.data.Genre
import dev.aliakbar.tmdbunofficial.data.Image
import dev.aliakbar.tmdbunofficial.data.MediaType
import dev.aliakbar.tmdbunofficial.data.Movie
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.data.source.sample.movie
import dev.aliakbar.tmdbunofficial.ui.components.CastItem
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicator
import dev.aliakbar.tmdbunofficial.ui.components.DetailsHeader
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.components.ListTitleText
import dev.aliakbar.tmdbunofficial.ui.components.MainDetailsRow
import dev.aliakbar.tmdbunofficial.ui.components.PersonList
import dev.aliakbar.tmdbunofficial.ui.components.ShowMoreDetailsButton
import dev.aliakbar.tmdbunofficial.ui.components.SubDetailsRow
import dev.aliakbar.tmdbunofficial.ui.components.TaglineText
import dev.aliakbar.tmdbunofficial.ui.components.TitleText
import dev.aliakbar.tmdbunofficial.ui.components.TopBar
import dev.aliakbar.tmdbunofficial.ui.components.VideoList
import dev.aliakbar.tmdbunofficial.util.calculateBackdropHeight
import dev.aliakbar.tmdbunofficial.util.share

@Composable
fun MovieScreen(
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToPerson: (Int) -> Unit,
    onNavigateToGenreTop: (Int,String, Boolean) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MovieViewModel = viewModel(factory = MovieViewModel.factory)
)
{
    when (val uiState = viewModel.movieUiState)
    {
        is MovieUiState.Loading -> CircularIndicator()
        is MovieUiState.Success -> MovieDetails(
            movie = uiState.movie,
            onNavigateToMovie = onNavigateToMovie,
            onNavigateToPerson = onNavigateToPerson,
            onNavigateToGenreTop = { genreId,genreName, type -> onNavigateToGenreTop(genreId,genreName, type) },
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
    onNavigateToPerson: (Int) -> Unit,
    onNavigateToGenreTop: (Int,String, Boolean) -> Unit,
    onNavigateBack: () -> Unit,
    addToBookmark: (Movie) -> Unit,
    removeFromBookmark: (Movie) -> Unit
)
{
    val scrollState = rememberScrollState()
    var showDetails by remember { mutableStateOf(false) }
    var showImageFullscreen by remember { mutableStateOf(false) }
    var selectedImagePath by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBar(
                title = movie.title,
                isBookmarkAlready = movie.isBookmark,
                onNavigateBack = onNavigateBack,
                onShare = { context.share(MediaType.MOVIE, movie.id) },
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
            val backdropHeight = calculateBackdropHeight(LocalConfiguration.current.screenWidthDp)

            Image(
                url = movie.backdropUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(backdropHeight.dp)
            )

            TitleText(title = movie.title, modifier = Modifier.padding(16.dp))

            MainDetailsRow(
                voteAverage = movie.voteAverage,
                runtime = movie.runtime,
                releaseDate = movie.releaseDate,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )

            if (!showDetails)
            {
                TaglineText(
                    tagline = movie.tagline,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )

                ShowMoreDetailsButton(
                    showMore = showDetails,
                    onClick = { showDetails = !showDetails },
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                )
            }
            else
            {
                Column(modifier = Modifier.padding(16.dp))
                {
                    TaglineText(
                        tagline = movie.tagline,
                        modifier = Modifier.padding(8.dp)
                    )

                    SubDetailsRow(
                        overview = movie.overview,
                        homepage = movie.homepage,
                        modifier = Modifier.padding(8.dp)
                    )

                    DetailsHeader(header = stringResource(R.string.genres))

                    GenreList(
                        genres = movie.genres,
                        onNavigateToGenreTop = onNavigateToGenreTop,
                        type = true
                    )

                    ShowMoreDetailsButton(
                        showMore = showDetails,
                        onClick = { showDetails = !showDetails },
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }
            }

            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp))
            {
                ListTitleText(
                    title = R.string.casts,
                    modifier = Modifier.padding(top = 32.dp, bottom = 8.dp)
                )

                PersonList(persons = movie.casts, onNavigateToPerson = onNavigateToPerson)

                ListTitleText(
                    title = R.string.crews,
                    modifier = Modifier.padding(top = 32.dp, bottom = 8.dp)
                )

                PersonList(persons = movie.crews, onNavigateToPerson = onNavigateToPerson)

                if (movie.videos.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.videos,
                        modifier = Modifier.padding(top = 32.dp, bottom = 8.dp)
                    )

                    VideoList(videos = movie.videos, {})
                }

                if (movie.posters.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.posters,
                        modifier = Modifier.padding(top = 32.dp, bottom = 8.dp)
                    )

                    PosterList(posters = movie.posters,
                        {
                            selectedImagePath = it.fileUrl
                            showImageFullscreen = true
                        })
                }

                if (movie.backdrops.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.backdrops,
                        modifier = Modifier.padding(top = 32.dp, bottom = 8.dp)
                    )
                    BackdropList(backdrops = movie.backdrops,
                        {
                            selectedImagePath = it.fileUrl
                            showImageFullscreen = true
                        })
                }

                if (movie.recommendations.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.recommendations,
                        modifier = Modifier.padding(top = 32.dp, bottom = 8.dp)
                    )

                    RecommendationList(
                        recommendations = movie.recommendations,
                        onNavigateToMovie = onNavigateToMovie
                    )
                }
            }
        }
    }

    if (showImageFullscreen)
    {
        ShowPosterInFullscreenDialog(posterUrl = selectedImagePath)
        {
            showImageFullscreen = false
        }
    }
}

@Composable
fun GenreList(
    genres: List<Genre>,
    type: Boolean,
    onNavigateToGenreTop: (Int, String, Boolean) -> Unit,
    modifier: Modifier = Modifier
)
{
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    )
    {
        items(items = genres)
        { genre ->
            TextButton(
                onClick = { onNavigateToGenreTop(genre.id,genre.name, type) },
            )
            {
                Text(text = genre.name)
            }
        }
    }
}

@Composable
fun CastList(
    casts: List<Cast>,
    onNavigateToPerson: (Int) -> Unit,
    modifier: Modifier = Modifier
)
{
    val scrollState = rememberLazyListState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = scrollState,
        modifier = Modifier.padding(bottom = 2.dp),
    )
    {
        items(items = casts)
        { cast ->
            CastItem(
                id = cast.id,
                name = cast.name,
                role = cast.character,
                pictureUrl = cast.profileUrl!!,
                onNavigateToPerson = onNavigateToPerson
            )
        }
    }

    Carousel(state = scrollState, modifier = Modifier.fillMaxWidth())
}

@Composable
fun CrewList(
    crews: List<Crew>,
    onNavigateToPerson: (Int) -> Unit,
    modifier: Modifier = Modifier
)
{
    val scrollState = rememberLazyListState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = scrollState,
        modifier = Modifier.padding(bottom = 2.dp)
    )
    {
        items(items = crews)
        { crew ->
            CastItem(
                id = crew.id,
                name = crew.name,
                role = crew.job,
                pictureUrl = crew.profileUrl!!,
                onNavigateToPerson = onNavigateToPerson
            )
        }
    }

    Carousel(state = scrollState, modifier = Modifier.fillMaxWidth())
}





@Composable
fun PosterList(posters: List<Image>, onPosterClick: (Image) -> Unit, modifier: Modifier = Modifier)
{
    val scrollState = rememberLazyListState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        state = scrollState,
        modifier = Modifier.padding(bottom = 1.dp)
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
        modifier = Modifier.padding(bottom = 1.dp)
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
        modifier = Modifier.padding(bottom = 1.dp)
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
        onClick = { onNavigateToMovie(recommendation.id) }
    )
    {
        Image(
            url = recommendation.poster,
            modifier = Modifier.size(width = 170.dp, height = 255.dp)
        )
    }
}

@Composable
fun ShowPosterInFullscreenDialog(
    posterUrl: String,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit
)
{
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale *= zoomChange
        offset += offsetChange
    }

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = { onDismissRequest() })
    {
        Image(url = posterUrl, modifier = Modifier
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationX = offset.x,
                translationY = offset.y
            )
            .transformable(state = state)
            )
    }
}

@Preview
@Composable
fun MovieDetailsPreview()
{
    MovieDetails(movie = movie, {}, {}, { _, _, _ -> }, {}, {}, {})
}
