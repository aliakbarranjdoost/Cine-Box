package dev.aliakbar.tmdbunofficial.ui.episode

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.EpisodeDetails
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicator
import dev.aliakbar.tmdbunofficial.ui.components.DetailsHeader
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.components.ListTitleText
import dev.aliakbar.tmdbunofficial.ui.components.MainMovieDetailsRow
import dev.aliakbar.tmdbunofficial.ui.components.PersonList
import dev.aliakbar.tmdbunofficial.ui.components.PosterList
import dev.aliakbar.tmdbunofficial.ui.components.ShowMoreDetailsButton
import dev.aliakbar.tmdbunofficial.ui.components.TitleText
import dev.aliakbar.tmdbunofficial.ui.components.TopBar
import dev.aliakbar.tmdbunofficial.ui.components.VideoList
import dev.aliakbar.tmdbunofficial.util.OVERVIEW_PREVIEW_MAX_LINE
import dev.aliakbar.tmdbunofficial.util.calculateBackdropHeight

@Composable
fun EpisodeScreen(
    onNavigateToPerson: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: EpisodeViewModel = hiltViewModel()
)
{
    when(val uiState = viewModel.episodeUiState)
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
            val backdropHeight = calculateBackdropHeight(LocalConfiguration.current.screenWidthDp)

            Image(
                url = episode.stillUrl!!, modifier = Modifier
                    .fillMaxWidth()
                    .height(backdropHeight.dp)
            )

            TitleText(title = episode.name, modifier = Modifier.padding(16.dp))

            MainMovieDetailsRow(
                voteAverage = episode.voteAverage,
                runtime = episode.runtime!!,
                releaseDate = episode.airDate,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )

            if (!showDetails)
            {
                Text(
                    text = episode.overview,
                    maxLines = OVERVIEW_PREVIEW_MAX_LINE,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
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
                    Text(text = episode.overview)

                    Row {
                        DetailsHeader(header = "Season ")
                        Text(text = episode.seasonNumber.toString())
                        DetailsHeader(header = " Episode ")
                        Text(text = episode.episodeNumber.toString())
                    }
                    if (episode.episodeType != null)
                    {
                        DetailsHeader(header = "Type")
                            Text(text = episode.episodeType)
                    }
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
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )

                PersonList(persons = episode.casts, onNavigateToPerson = onNavigateToPerson)

                ListTitleText(title = R.string.crews,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))

                PersonList(persons = episode.crews, onNavigateToPerson = onNavigateToPerson)

                if (episode.guestStars.isNotEmpty())
                {
                    ListTitleText(title = R.string.guest_stars,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
                    PersonList(
                        persons = episode.guestStars,
                        onNavigateToPerson = onNavigateToPerson
                    )
                }

                if (episode.stills.isNotEmpty())
                {
                    ListTitleText(title = R.string.stills,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
                    PosterList(posters = episode.stills,
                        {
                            selectedImagePath = it.fileUrl
                            showPosterFullscreen = true
                        })
                }

                if (episode.videos.isNotEmpty())
                {
                    ListTitleText(title = R.string.videos,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
                    VideoList(videos = episode.videos, {})
                }
            }
        }
    }
}