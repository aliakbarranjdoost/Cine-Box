package dev.aliakbar.tmdbunofficial.ui.tv

import Carousel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.MediaType
import dev.aliakbar.tmdbunofficial.data.Season
import dev.aliakbar.tmdbunofficial.data.Tv
import dev.aliakbar.tmdbunofficial.ui.components.BackdropList
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicator
import dev.aliakbar.tmdbunofficial.ui.components.DetailsHeader
import dev.aliakbar.tmdbunofficial.ui.components.GenreList
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.components.ListTitleText
import dev.aliakbar.tmdbunofficial.ui.components.MainTvDetailsRow
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
fun TvScreen(
    onNavigateToTv: (Int) -> Unit,
    onNavigateToSeason: (Int, Int) -> Unit,
    onNavigateToPerson: (Int) -> Unit,
    onNavigateToGenreTop: (Int, String, Boolean) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TvViewModel = hiltViewModel()
)
{
    when (val uiState = viewModel.tvUiState)
    {
        is TvUiState.Loading -> CircularIndicator()
        is TvUiState.Success ->
            TvDetails(
                tv = uiState.tv,
                onNavigateToTv = onNavigateToTv,
                onNavigateToSeason = onNavigateToSeason,
                onNavigateToPerson = onNavigateToPerson,
                onNavigateToGenreTop = { genreId, genreName, type ->
                    onNavigateToGenreTop(
                        genreId,
                        genreName,
                        type
                    )
                },
                onNavigateBack = onNavigateBack,
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
    onNavigateToPerson: (Int) -> Unit,
    onNavigateToGenreTop: (Int, String, Boolean) -> Unit,
    onNavigateBack: () -> Unit,
    addToBookmark: () -> Unit,
    removeFromBookmark: () -> Unit,
    modifier: Modifier = Modifier
)
{
    val scrollState = rememberScrollState()
    var showDetails by remember { mutableStateOf(false) }
    var showPosterFullscreen by remember { mutableStateOf(false) }
    var selectedImagePath by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBar(
                title = tv.name,
                isBookmarkAlready = tv.isBookmark,
                onNavigateBack = onNavigateBack,
                onShare = { context.share(MediaType.TV, tv.id) },
                addToBookmark = addToBookmark,
                removeFromBookmark = removeFromBookmark
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
                url = tv.backdropUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(backdropHeight.dp)
            )

            MainTvDetailsRow(
                voteAverage = tv.voteAverage,
                seasonNumber = tv.numberOfSeasons,
                releaseDate = tv.firstAirDate,
                modifier = Modifier.padding(16.dp)
            )

            if (tv.tagline.isNotEmpty())
            {
                TaglineText(
                    tagline = tv.tagline,
                    modifier = Modifier.padding(horizontal = 16.dp)
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
                Column(modifier = Modifier.padding(16.dp))
                {
                    SubDetailsRow(overview = tv.overview, homepage = tv.homepage)

                    DetailsHeader(header = stringResource(R.string.seasons))
                    Text(text = tv.numberOfSeasons.toString())

                    DetailsHeader(header = stringResource(R.string.episodes))
                    Text(text = tv.numberOfEpisodes.toString())

                    DetailsHeader(header = stringResource(R.string.genres))
                    GenreList(
                        genres = tv.genres,
                        onNavigateToGenreTop = onNavigateToGenreTop,
                        type = false
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
                    title = R.string.seasons,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                SeasonList(
                    seasons = tv.seasons,
                    onNavigateToSeason = { onNavigateToSeason(tv.id, it) }
                )

                ListTitleText(
                    title = R.string.casts,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
                PersonList(persons = tv.casts, onNavigateToPerson = onNavigateToPerson)

                ListTitleText(
                    title = R.string.crews,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
                PersonList(persons = tv.crews, onNavigateToPerson = onNavigateToPerson)

                if (tv.videos.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.videos,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                    VideoList(videos = tv.videos, {})
                }

                if (tv.posters.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.posters,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                    PosterList(posters = tv.posters,
                        {
                            selectedImagePath = it.fileUrl
                            showPosterFullscreen = true
                        })
                }

                if (tv.backdrops.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.backdrops,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                    BackdropList(backdrops = tv.backdrops,
                        {
                            selectedImagePath = it.fileUrl
                            showPosterFullscreen = true
                        })
                }

                if (tv.recommendations.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.recommendations,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )

                    RecommendationList(
                        recommendations = tv.recommendations,
                        onNavigateToRecommend = onNavigateToTv
                    )
                }
            }
        }
    }

    if (showPosterFullscreen)
    {
        ShowImageInFullscreenDialog(posterUrl = selectedImagePath)
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
    val scrollState = rememberLazyListState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = scrollState,
        modifier = Modifier.padding(bottom = 2.dp)
    )
    {
        items(items = seasons)
        { season ->
            SeasonItem(season = season, onNavigateToSeason = onNavigateToSeason)
        }
    }
    Carousel(state = scrollState, modifier = Modifier.fillMaxWidth())
}

@Composable
fun SeasonItem(season: Season, onNavigateToSeason: (Int) -> Unit)
{
    ElevatedCard(
        onClick = { onNavigateToSeason(season.seasonNumber) },
        modifier = Modifier
            .width(200.dp)
    )
    {
        Image(
            url = season.posterPath,
            modifier = Modifier
                .width(200.dp)
                .height(300.dp)
        )

        Text(
            text = season.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = season.airDate.substring(0..3),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )
        Text(
            text = "${season.episodeCount} ${stringResource(id = R.string.episodes)}",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(8.dp)
        )
    }
}