package dev.aliakbar.tmdbunofficial.ui.episode

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.EpisodeDetails
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicator
import dev.aliakbar.tmdbunofficial.ui.components.DetailsHeader
import dev.aliakbar.tmdbunofficial.ui.components.ErrorButton
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.components.ListTitleText
import dev.aliakbar.tmdbunofficial.ui.components.MainMovieDetailsRow
import dev.aliakbar.tmdbunofficial.ui.components.PersonList
import dev.aliakbar.tmdbunofficial.ui.components.PosterList
import dev.aliakbar.tmdbunofficial.ui.components.ShowMoreDetailsButton
import dev.aliakbar.tmdbunofficial.ui.components.TopBar
import dev.aliakbar.tmdbunofficial.ui.components.VideoList
import dev.aliakbar.tmdbunofficial.util.OVERVIEW_PREVIEW_MAX_LINE
import dev.aliakbar.tmdbunofficial.util.calculateBackdropHeight

@Composable
fun EpisodeScreen(
    onNavigateToPerson: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EpisodeViewModel = hiltViewModel()
)
{
    when(val uiState = viewModel.episodeUiState)
    {
        is EpisodeUiState.Loading -> CircularIndicator()
        is EpisodeUiState.Error -> ErrorButton { viewModel.getEpisodeDetails() }
        is EpisodeUiState.Success ->
        {
            EpisodeScreen(
                episode = uiState.episode,
                onNavigateToPerson = onNavigateToPerson,
                onNavigateBack = onNavigateBack)
        }
    }
}

@Composable
fun EpisodeScreen(
    episode: EpisodeDetails,
    onNavigateToPerson: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
)
{
    val scrollState = rememberScrollState()
    var showDetails by remember { mutableStateOf(false) }
    var showPosterFullscreen by remember { mutableStateOf(false) }
    var selectedImagePath by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopBar(title = episode.name, onNavigateBack = onNavigateBack) }
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
                url = episode.stillUrl!!, modifier = Modifier
                    .fillMaxWidth()
                    .height(backdropHeight.dp)
            )

            MainMovieDetailsRow(
                voteAverage = episode.voteAverage,
                runtime = episode.runtime!!,
                releaseDate = episode.airDate,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            )

            if (!showDetails)
            {
                Text(
                    text = episode.overview,
                    maxLines = OVERVIEW_PREVIEW_MAX_LINE,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
                )

                ShowMoreDetailsButton(
                    showMore = showDetails,
                    onClick = { showDetails = !showDetails },
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                )
            }
            AnimatedVisibility(visible = showDetails)
            {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
                )
                {
                    Text(text = episode.overview)

                    Row(
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_large))
                    ) {
                        DetailsHeader(header = stringResource(R.string.season_number))
                        Text(text = episode.seasonNumber.toString())
                        DetailsHeader(header = stringResource(R.string.episode_number))
                        Text(text = episode.episodeNumber.toString())
                    }
                    if (episode.episodeType != null)
                    {
                        DetailsHeader(header = stringResource(R.string.type))
                        Text(text = episode.episodeType)
                    }
                    ShowMoreDetailsButton(
                        showMore = showDetails,
                        onClick = { showDetails = !showDetails },
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }
            }

            val topPadding = dimensionResource(id = R.dimen.padding_large)
            val bottomPadding = dimensionResource(id = R.dimen.padding_medium)

            Column(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_large)))
            {
                if (episode.casts.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.casts,
                        modifier = Modifier.padding(bottom = bottomPadding)
                    )

                    PersonList(
                        persons = episode.casts,
                        onNavigateToPerson = onNavigateToPerson
                    )
                }

                if (episode.crews.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.crews,
                        modifier = Modifier.padding(top = topPadding, bottom = bottomPadding)
                    )

                    PersonList(
                        persons = episode.crews,
                        onNavigateToPerson = onNavigateToPerson
                    )
                }

                if (episode.guestStars.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.guest_stars,
                        modifier = Modifier.padding(top = topPadding, bottom = bottomPadding)
                    )
                    PersonList(
                        persons = episode.guestStars,
                        onNavigateToPerson = onNavigateToPerson
                    )
                }

                if (episode.stills.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.stills,
                        modifier = Modifier.padding(top = topPadding, bottom = bottomPadding)
                    )
                    PosterList(
                        posters = episode.stills,
                        onPosterClick =
                        {
                            selectedImagePath = it.fileUrl
                            showPosterFullscreen = true
                        }
                    )
                }

                if (episode.videos.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.videos,
                        modifier = Modifier.padding(top = topPadding, bottom = bottomPadding)
                    )
                    VideoList(videos = episode.videos)
                }
            }
        }
    }
}