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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.EpisodeDetails
import dev.aliakbar.tmdbunofficial.ui.components.ScoreBar
import dev.aliakbar.tmdbunofficial.ui.components.TopBar
import dev.aliakbar.tmdbunofficial.ui.details.BackdropList
import dev.aliakbar.tmdbunofficial.ui.details.CastList
import dev.aliakbar.tmdbunofficial.ui.details.CrewList
import dev.aliakbar.tmdbunofficial.ui.details.DetailsHeader
import dev.aliakbar.tmdbunofficial.ui.details.GenreList
import dev.aliakbar.tmdbunofficial.ui.details.ListHeader
import dev.aliakbar.tmdbunofficial.ui.details.OVERVIEW_PREVIEW_MAX_LINE
import dev.aliakbar.tmdbunofficial.ui.details.PosterList
import dev.aliakbar.tmdbunofficial.ui.details.RecommendationList
import dev.aliakbar.tmdbunofficial.ui.details.SeasonList
import dev.aliakbar.tmdbunofficial.ui.details.VideoList

@Composable
fun EpisodeScreen(
    viewModel: EpisodeViewModel = viewModel(factory = EpisodeViewModel.factory),
)
{
    val uiState = viewModel.episodeUiState

    when(uiState)
    {
        is EpisodeUiState.Loading -> Text(text = "Loading")
        is EpisodeUiState.Error -> Text(text = "Error")
        is EpisodeUiState.Success ->
        {
            EpisodeScreen(uiState.episode)
        }
    }
}

@Composable
fun EpisodeScreen(
    episode: EpisodeDetails
)
{
    val scrollState = rememberScrollState()
    var showDetails by remember { mutableStateOf(false) }
    var showPosterFullscreen by remember { mutableStateOf(false) }
    var selectedImagePath by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopBar(title = episode.name) }
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
                        .data(episode.stillUrl)
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

            ListHeader(header = "Cast")

            CastList(casts = episode.casts)

            ListHeader(header = "Crew")

            CrewList(crews = episode.crews)

            ListHeader(header = "Guest Stars")

            CastList(casts = episode.guestStars)

            ListHeader(header = "Stills")

            PosterList(posters = episode.stills,
                {
                    selectedImagePath = it.fileUrl
                    showPosterFullscreen = true
                })

            ListHeader(header = "Videos")

            VideoList(videos = episode.videos, {})
        }
    }
}