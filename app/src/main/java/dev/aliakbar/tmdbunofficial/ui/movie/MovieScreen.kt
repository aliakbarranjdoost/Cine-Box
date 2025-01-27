package dev.aliakbar.tmdbunofficial.ui.movie

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.MediaType
import dev.aliakbar.tmdbunofficial.data.Movie
import dev.aliakbar.tmdbunofficial.data.source.sample.movie
import dev.aliakbar.tmdbunofficial.ui.components.BackdropList
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicator
import dev.aliakbar.tmdbunofficial.ui.components.DetailsHeader
import dev.aliakbar.tmdbunofficial.ui.components.ErrorButton
import dev.aliakbar.tmdbunofficial.ui.components.GenreList
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.components.ListTitleText
import dev.aliakbar.tmdbunofficial.ui.components.MainMovieDetailsRow
import dev.aliakbar.tmdbunofficial.ui.components.PersonList
import dev.aliakbar.tmdbunofficial.ui.components.PosterList
import dev.aliakbar.tmdbunofficial.ui.components.RecommendationList
import dev.aliakbar.tmdbunofficial.ui.components.ShowImageInFullscreenDialog
import dev.aliakbar.tmdbunofficial.ui.components.ShowMoreDetailsButton
import dev.aliakbar.tmdbunofficial.ui.components.SubDetailsRow
import dev.aliakbar.tmdbunofficial.ui.components.TaglineText
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
    viewModel: MovieViewModel = hiltViewModel()
)
{
    when (val uiState = viewModel.movieUiState)
    {
        is MovieUiState.Loading -> CircularIndicator()
        is MovieUiState.Error   -> ErrorButton(onClick = { viewModel.getMovieDetails() })
        is MovieUiState.Success -> MovieDetails(
            movie = uiState.movie,
            onNavigateToMovie = onNavigateToMovie,
            onNavigateToPerson = onNavigateToPerson,
            onNavigateToGenreTop = { genreId,genreName, type -> onNavigateToGenreTop(genreId,genreName, type) },
            onNavigateBack = onNavigateBack,
            addToBookmark = { viewModel.addToBookmark(it) },
            removeFromBookmark = { viewModel.removeFromBookmark(it) },
            modifier = modifier
        )
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
    removeFromBookmark: (Movie) -> Unit,
    modifier: Modifier = Modifier
)
{
    var showDetails by remember { mutableStateOf(false) }
    var showImageFullscreen by remember { mutableStateOf(false) }
    var selectedImagePath by remember { mutableStateOf("") }
    val topPadding = dimensionResource(id = R.dimen.padding_large)
    val bottomPadding = dimensionResource(id = R.dimen.padding_medium)
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
        },
        modifier = modifier
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        )
        {
            Image(
                url = movie.backdropUrl,
                modifier = Modifier.fillMaxWidth()
            )

            MainMovieDetailsRow(
                voteAverage = movie.voteAverage,
                runtime = movie.runtime,
                releaseDate = movie.releaseDate,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_large))
            )

            if (movie.tagline.isNotEmpty())
            {
                TaglineText(
                    tagline = movie.tagline,
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_large))
                )
            }

            if (!showDetails)
            {
                ShowMoreDetailsButton(
                    showMore = showDetails,
                    onClick = { showDetails = !showDetails },
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                )
            }

            AnimatedVisibility(visible = showDetails)
            {
                Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)))
                {
                    SubDetailsRow(
                        overview = movie.overview,
                        homepage = movie.homepage
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

            Column()
            {
                ListTitleText(
                    title = R.string.casts,
                    modifier = Modifier
                        .padding(bottom = bottomPadding)
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
                )

                PersonList(
                    persons = movie.casts,
                    onNavigateToPerson = onNavigateToPerson
                )

                ListTitleText(
                    title = R.string.crews,
                    modifier = Modifier
                        .padding(top = topPadding, bottom = bottomPadding)
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
                )

                PersonList(
                    persons = movie.crews,
                    onNavigateToPerson = onNavigateToPerson
                )

                if (movie.videos.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.videos,
                        modifier = Modifier
                            .padding(top = topPadding, bottom = bottomPadding)
                            .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
                    )

                    VideoList(videos = movie.videos)
                }

                if (movie.posters.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.posters,
                        modifier = Modifier
                            .padding(top = topPadding, bottom = bottomPadding)
                            .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
                    )

                    PosterList(
                        posters = movie.posters,
                        onPosterClick =
                        {
                            selectedImagePath = it.fileUrl
                            showImageFullscreen = true
                        }
                    )
                }

                if (movie.backdrops.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.backdrops,
                        modifier = Modifier
                            .padding(top = topPadding, bottom = bottomPadding)
                            .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
                    )
                    BackdropList(
                        backdrops = movie.backdrops,
                        onPosterClick =
                        {
                            selectedImagePath = it.fileUrl
                            showImageFullscreen = true
                        }
                    )
                }

                if (movie.recommendations.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.recommendations,
                        modifier = Modifier
                            .padding(top = topPadding, bottom = bottomPadding)
                            .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
                    )

                    RecommendationList(
                        recommendations = movie.recommendations,
                        onNavigateToRecommend = onNavigateToMovie
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
        }
    }

    if (showImageFullscreen)
    {
        ShowImageInFullscreenDialog(
            posterUrl = selectedImagePath,
            onDismissRequest = { showImageFullscreen = false }
        )
    }
}

@Preview
@Composable
fun MovieDetailsPreview()
{
    MovieDetails(movie = movie, {}, {}, { _, _, _ -> }, {}, {}, {})
}
