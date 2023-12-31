package dev.aliakbar.tmdbunofficial.ui.episode

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Episode
import dev.aliakbar.tmdbunofficial.data.SeasonDetails
import dev.aliakbar.tmdbunofficial.data.source.sample.episode
import dev.aliakbar.tmdbunofficial.data.source.sample.episodes
import dev.aliakbar.tmdbunofficial.ui.components.Rank
import dev.aliakbar.tmdbunofficial.ui.components.ScoreBar

@Composable
fun SeasonDetailsScreen(
    viewModel: EpisodeListViewModel = viewModel(factory = EpisodeListViewModel.factory)
)
{
    val uiState = viewModel.episodeListUiState

    when (uiState)
    {
        is SeasonDetailsUiState.Loading -> Text(text = "Loading")
        is SeasonDetailsUiState.Success ->
        {
            SeasonDetailsScreen(seasonDetails = uiState.seasonDetails)
        }

        is SeasonDetailsUiState.Error   -> Text(text = "Error")
    }
}

@Composable
fun SeasonDetailsScreen(
    seasonDetails: SeasonDetails
)
{
    EpisodeList(episodes = seasonDetails.episodes)
}

@Composable
fun EpisodeList(
    episodes: List<Episode>
)
{
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {
        items(items = episodes)
        {
            EpisodeItem(episode = it)
        }
    }
}

@Composable
fun EpisodeItem(episode: Episode)
{
    Row(Modifier.height(100.dp)) {
        Box(
            modifier = Modifier
                .width(150.dp)
                .height(100.dp)
        )
        {
            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .data(episode.stillUrl)
                    .build(),
                placeholder = painterResource(id = R.drawable.backdrop_test),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,

                )
            Rank(
                rank = episode.episodeNumber, modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.TopStart)
                    .padding(start = 2.dp, top = 2.dp)
            )

            ScoreBar(
                score = episode.voteAverage,
                modifier =
                Modifier.size(40.dp)
                    .align(Alignment.BottomStart))
        }

        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = episode.episodeNumber.toString() + ". " + episode.name)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = episode.overview, maxLines = 3)
        }
    }
}

@Preview
@Composable
fun PreviewEpisodeItem()
{
    EpisodeItem(episode = episode)
}

@Preview
@Composable
fun PreviewEpisodeList()
{
    EpisodeList(episodes = episodes)
}