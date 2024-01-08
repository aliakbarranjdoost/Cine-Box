package dev.aliakbar.tmdbunofficial.ui.tv

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.aliakbar.tmdbunofficial.data.Tv
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicator
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.components.ScoreBar
import dev.aliakbar.tmdbunofficial.ui.movie.BackdropList
import dev.aliakbar.tmdbunofficial.ui.movie.CastList
import dev.aliakbar.tmdbunofficial.ui.movie.CrewList
import dev.aliakbar.tmdbunofficial.ui.movie.DetailsHeader
import dev.aliakbar.tmdbunofficial.ui.movie.MovieUiState
import dev.aliakbar.tmdbunofficial.ui.movie.MovieViewModel
import dev.aliakbar.tmdbunofficial.ui.movie.GenreList
import dev.aliakbar.tmdbunofficial.ui.movie.ListHeader
import dev.aliakbar.tmdbunofficial.ui.movie.MovieDetails
import dev.aliakbar.tmdbunofficial.ui.movie.OVERVIEW_PREVIEW_MAX_LINE
import dev.aliakbar.tmdbunofficial.ui.movie.PosterList
import dev.aliakbar.tmdbunofficial.ui.movie.RecommendationList
import dev.aliakbar.tmdbunofficial.ui.movie.SeasonList
import dev.aliakbar.tmdbunofficial.ui.movie.ShowPosterInFullscreenDialog
import dev.aliakbar.tmdbunofficial.ui.movie.TopBar
import dev.aliakbar.tmdbunofficial.ui.movie.VideoList

@Composable
fun TvScreen(
    onNavigateToTv: (Int) -> Unit,
    onNavigateToSeason: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MovieViewModel = viewModel(factory = MovieViewModel.factory)
)
{
    val uiState = viewModel.movieUiState

    when (uiState)
    {
        is MovieUiState.Loading      -> CircularIndicator()
        is MovieUiState.SuccessMovie -> MovieDetails(
            movie = uiState.movie,
            onNavigateToMovie = onNavigateToMovie,
            addToBookmark = { viewModel.addToBookmark(it) },
            removeFromBookmark = { viewModel.removeFromBookmark(it)}
        )
        is MovieUiState.SuccessTv    -> Text(text = "Tv")
        /*TvDetails(
            tv = uiState.tv,
            onNavigateToMovie = onNavigateToMovie,
            addToBookmark = addToBookmark,
            removeFromBookmark = removeFromBookmark,
            onSeasonClick = { *//*navController.navigate(TmdbScreen.SeasonDetails.name + "/" + uiState.tv.id + "/" + it)*//* }
            )*/
        is MovieUiState.Error        -> Text(text = "Error")
    }
}

@Composable
fun TvDetails(
    tv: Tv,
    onNavigateToMovie: (Int) -> Unit,
    onSeasonClick: (Int) -> Unit,
    addToBookmark: () -> Unit,
    removeFromBookmark: () -> Unit,
    modifier: Modifier = Modifier
)
{
    val scrollState = rememberScrollState()
    var showDetails by remember { mutableStateOf(false) }
    var showPosterFullscreen by remember { mutableStateOf(false) }
    var selectedImagePath by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopBar(title = tv.name,
            // TODO: add isBookmark property to tv model later
            isBookmarkAlready = false,
            addToBookmark = addToBookmark,
            removeFromBookmark = removeFromBookmark
        ) }
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
                    url = tv.backdropUrl, modifier = Modifier
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

                RecommendationList(
                    recommendations = tv.recommendations,
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
