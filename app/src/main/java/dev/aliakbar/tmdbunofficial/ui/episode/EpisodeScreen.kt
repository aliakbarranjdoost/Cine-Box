package dev.aliakbar.tmdbunofficial.ui.episode

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
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.EpisodeDetails
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicator
import dev.aliakbar.tmdbunofficial.ui.components.DetailsHeader
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.components.ListTitleText
import dev.aliakbar.tmdbunofficial.ui.components.PersonList
import dev.aliakbar.tmdbunofficial.ui.components.ScoreBar
import dev.aliakbar.tmdbunofficial.ui.components.TopBar
import dev.aliakbar.tmdbunofficial.ui.components.VideoList
import dev.aliakbar.tmdbunofficial.ui.movie.PosterList
import dev.aliakbar.tmdbunofficial.util.OVERVIEW_PREVIEW_MAX_LINE

@Composable
fun EpisodeScreen(
    onNavigateToPerson: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: EpisodeViewModel = viewModel(factory = EpisodeViewModel.factory),
)
{
    val uiState = viewModel.episodeUiState

    when(uiState)
    {
        is EpisodeUiState.Loading -> CircularIndicator()
        is EpisodeUiState.Error -> Text(text = "Error")
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
    onNavigateBack: () -> Unit
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            {
                Image(
                    url = episode.stillUrl!!, modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .align(Alignment.Center)
                )
                ScoreBar(
                    score = episode.voteAverage,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }

            if (!showDetails)
            {
                Text(
                    text = episode.overview,
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
                    Text(text = episode.overview)
                    DetailsHeader(header = "Runtime")
                    Text(text = episode.runtime.toString())
                    DetailsHeader(header = "Release Date")
                    Text(text = episode.airDate)
                    DetailsHeader(header = "Season")
                    Text(text = episode.seasonNumber.toString())
                    DetailsHeader(header = "Episode")
                    Text(text = episode.episodeNumber.toString())
                    if (episode.episodeType != null)
                    {
                        DetailsHeader(header = "Type")
                        Text(text = episode.episodeType)
                    }
                    TextButton(
                        onClick = { showDetails = false },
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    )
                    {
                        Text(text = "less Details")
                    }
                }
            }

            ListTitleText(title = R.string.casts)

            PersonList(persons = episode.casts, onNavigateToPerson = onNavigateToPerson)

            ListTitleText(title = R.string.crews)

            PersonList(persons = episode.crews, onNavigateToPerson = onNavigateToPerson)

            ListTitleText(title = R.string.guest_stars)

            PersonList(persons = episode.guestStars, onNavigateToPerson = onNavigateToPerson)

            if (episode.stills.isNotEmpty())
            {
                ListTitleText(title = R.string.stills)
                PosterList(posters = episode.stills,
                    {
                        selectedImagePath = it.fileUrl
                        showPosterFullscreen = true
                    })
            }

            if (episode.videos.isNotEmpty())
            {
                ListTitleText(title = R.string.videos)
                VideoList(videos = episode.videos, {})
            }
        }
    }
}