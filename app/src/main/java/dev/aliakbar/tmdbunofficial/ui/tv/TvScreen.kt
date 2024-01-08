package dev.aliakbar.tmdbunofficial.ui.tv

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
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
import dev.aliakbar.tmdbunofficial.data.Season
import dev.aliakbar.tmdbunofficial.data.Tv
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicator
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.components.ScoreBar
import dev.aliakbar.tmdbunofficial.ui.movie.BackdropList
import dev.aliakbar.tmdbunofficial.ui.movie.CastList
import dev.aliakbar.tmdbunofficial.ui.movie.CrewList
import dev.aliakbar.tmdbunofficial.ui.movie.DetailsHeader
import dev.aliakbar.tmdbunofficial.ui.movie.GenreList
import dev.aliakbar.tmdbunofficial.ui.movie.ListHeader
import dev.aliakbar.tmdbunofficial.ui.movie.OVERVIEW_PREVIEW_MAX_LINE
import dev.aliakbar.tmdbunofficial.ui.movie.PosterList
import dev.aliakbar.tmdbunofficial.ui.movie.RecommendationList
import dev.aliakbar.tmdbunofficial.ui.movie.ShowPosterInFullscreenDialog
import dev.aliakbar.tmdbunofficial.ui.movie.TopBar
import dev.aliakbar.tmdbunofficial.ui.movie.VideoList

@Composable
fun TvScreen(
    onNavigateToTv: (Int) -> Unit,
    onNavigateToSeason: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TvViewModel = viewModel(factory = TvViewModel.factory)
)
{
    val uiState = viewModel.tvUiState

    when (uiState)
    {
        is TvUiState.Loading -> CircularIndicator()
        is TvUiState.Success ->
            TvDetails(
                tv = uiState.tv,
                onNavigateToTv = onNavigateToTv,
                onNavigateToSeason = onNavigateToSeason,
                addToBookmark = { viewModel.addToBookmark(uiState.tv) },
                removeFromBookmark = { viewModel.removeFromBookmark(uiState.tv) },
            )

        is TvUiState.Error   -> Text(text = "Error")
    }
}

@Composable
fun TvDetails(
    tv: Tv,
    onNavigateToTv: (Int) -> Unit,
    onNavigateToSeason: (Int, Int) -> Unit,
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

            SeasonList(
                seasons = tv.seasons,
                onNavigateToSeason = { onNavigateToSeason(tv.id, it) }
            )

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
                    onNavigateToMovie = onNavigateToTv
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
fun SeasonList(
    seasons: List<Season>,
    onNavigateToSeason: (Int) -> Unit,
    modifier: Modifier = Modifier
)
{
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
    {
        items(items = seasons)
        { season ->
            SeasonItem(season = season, onNavigateToSeason = onNavigateToSeason)
        }
    }
}

@Composable
fun SeasonItem(season: Season, onNavigateToSeason: (Int) -> Unit)
{
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(400.dp),
        onClick = { onNavigateToSeason(season.seasonNumber) }
    )
    {
        Column()
        {
            Image(url = season.posterPath,
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