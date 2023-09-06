@file:OptIn(ExperimentalMaterial3Api::class)

package dev.aliakbar.tmdbunofficial.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Cast
import dev.aliakbar.tmdbunofficial.data.Crew
import dev.aliakbar.tmdbunofficial.data.Image
import dev.aliakbar.tmdbunofficial.data.Movie
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.data.Video
import dev.aliakbar.tmdbunofficial.data.source.sample.movie

@Composable
fun DetailsScreen(
        viewModel: DetailsViewModel = viewModel(factory = DetailsViewModel.factory)
)
{
    val uiState = viewModel.detailsUiState

    when(uiState)
    {
        is DetailsUiState.Loading -> Text(text = "Loading")
        is DetailsUiState.Success -> MovieDetails(movie = uiState.movie)
        is DetailsUiState.Error -> Text(text = "Error")
    }
}

@Composable
fun MovieDetails(movie: Movie)
{
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { TopBar() },
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    )
    { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding))
        {
            AsyncImage(
                model = ImageRequest
                    .Builder( context = LocalContext.current)
                    .data(movie.backdropPath)
                    .build(),
                placeholder = painterResource(id = R.drawable.backdrop_test),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Text(text = movie.title)

            Text(text = movie.voteAverage.toString())

            Text(text = movie.overview)

            CastList(casts = movie.casts)

            CrewList(crews = movie.crews)

            VideoList(videos = movie.videos, onVideoClick = { /*TODO*/ })

            PosterList(posters = movie.posters)

            BackdropList(backdrops = movie.backdrops)

            RecommendationList(recommendations = movie.recommendations)
        }
    }
}

@Composable
fun CastList(casts: List<Cast>, modifier: Modifier = Modifier)
{
    LazyRow( )
    {
        items(items = casts)
        {
            cast ->
            CastItem(cast = cast, onCastClick = { })
        }
    }
}

@Composable
fun CastItem(cast: Cast,onCastClick: () -> Unit, modifier: Modifier = Modifier)
{
    Card(modifier = Modifier.padding(16.dp), onClick = onCastClick)
    {
        AsyncImage(
            model = ImageRequest
            .Builder(context = LocalContext.current)
            .data(cast.profilePath)
            .build(),
            placeholder = painterResource(id = R.drawable.profile_test),
            contentDescription = null
        )

        Text(text = cast.name)
        Text(text = cast.character)
    }
}

@Composable
fun CrewList(crews: List<Crew>, modifier: Modifier = Modifier)
{
    LazyRow( )
    {
        items(items = crews)
        {
                crew ->
            CrewItem(crew = crew, onCastClick = { })
        }
    }
}

@Composable
fun CrewItem(crew: Crew,onCastClick: () -> Unit, modifier: Modifier = Modifier)
{
    Card(modifier = Modifier.padding(16.dp), onClick = onCastClick)
    {
        AsyncImage(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .data(crew.profilePath)
                .build(),
            placeholder = painterResource(id = R.drawable.profile_test),
            contentDescription = null
        )

        Text(text = crew.name)
        Text(text = crew.job)
    }
}

@Composable
fun VideoList(videos: List<Video>, onVideoClick: () -> Unit, modifier: Modifier = Modifier)
{
    LazyRow()
    {
        items(videos)
        {
            video ->
            VideoItem(video = video, onVideoClick)
        }
    }
}

@Composable
fun VideoItem(video: Video, onVideoClick: () -> Unit, modifier: Modifier = Modifier)
{
    Card(modifier = Modifier.padding(16.dp), onClick = onVideoClick)
    {
        Image(
            painter = painterResource(id = R.drawable.backdrop_test),
            contentDescription = null
        )
    }
}

@Composable
fun PosterList(posters: List<Image>, modifier: Modifier = Modifier)
{
    LazyRow( )
    {
        items(items = posters)
        {
                poster ->
            PosterItem(poster = poster)
        }
    }
}

@Composable
fun PosterItem(poster: Image)
{
    Card(modifier = Modifier.padding(16.dp))
    {
        AsyncImage(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .data(poster.filePath)
                .build(),
            placeholder = painterResource(id = R.drawable.poster_test),
            contentDescription = null
        )
    }
}

@Composable
fun BackdropList(backdrops: List<Image>, modifier: Modifier = Modifier)
{
    LazyRow( )
    {
        items(items = backdrops)
        {
                backdrop ->
            BackdropItem(backdrop = backdrop)
        }
    }
}

@Composable
fun BackdropItem(backdrop: Image)
{
    Card(modifier = Modifier.padding(16.dp))
    {
        AsyncImage(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .data(backdrop.filePath)
                .build(),
            placeholder = painterResource(id = R.drawable.backdrop_test),
            contentDescription = null
        )
    }
}

@Composable
fun RecommendationList(recommendations: List<Trend>, modifier: Modifier = Modifier)
{
    LazyRow( )
    {
        items(items = recommendations)
        {
                recommendation ->
            RecommendationItem(recommendation = recommendation)
        }
    }
}

@Composable
fun RecommendationItem(recommendation: Trend)
{
    Card(modifier = Modifier.padding(16.dp))
    {
        Column()
        {
            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .data(recommendation.poster)
                    .build(),
                placeholder = painterResource(id = R.drawable.poster_test),
                contentDescription = null
            )

            Text(text = recommendation.title)

            Text(text = recommendation.score.toString())
        }
    }
}
@Composable
fun TopBar()
{
    CenterAlignedTopAppBar(title = {
        Text(
            "Centered TopAppBar",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    },
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Localized description"
                )
            }
        })
}

@Preview
@Composable
fun MovieDetailsPreview()
{
    MovieDetails(movie = movie)
}