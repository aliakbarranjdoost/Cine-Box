@file:OptIn(ExperimentalMaterial3Api::class)

package dev.aliakbar.tmdbunofficial.ui.season

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import dev.aliakbar.tmdbunofficial.data.Episode
import dev.aliakbar.tmdbunofficial.data.SeasonDetails
import dev.aliakbar.tmdbunofficial.data.source.sample.episode
import dev.aliakbar.tmdbunofficial.data.source.sample.episodes
import dev.aliakbar.tmdbunofficial.data.source.sample.seasonDetails
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicator
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.components.Rank
import dev.aliakbar.tmdbunofficial.ui.components.ScoreBar
import dev.aliakbar.tmdbunofficial.ui.components.TopBar
import dev.aliakbar.tmdbunofficial.ui.theme.TMDBUnofficialTheme

@Composable
fun SeasonDetailsScreen(
    navController: NavHostController,
    viewModel: SeasonDetailsViewModel = viewModel(factory = SeasonDetailsViewModel.factory)
)
{
    val uiState = viewModel.episodeListUiState

    when (uiState)
    {
        is SeasonDetailsUiState.Loading -> CircularIndicator()
        is SeasonDetailsUiState.Success ->
        {
            SeasonDetailsScreen(seasonDetails = uiState.seasonDetails,
                onEpisodeClick =
                {
/*
                    navController.navigate(
                        TmdbScreen.EpisodeDetails.name + "/" + viewModel.id
                                + "/" + it.seasonNumber + "/" + it.episodeNumber)
*/
                })
        }

        is SeasonDetailsUiState.Error   -> Text(text = "Error")
    }
}

@Composable
fun SeasonDetailsScreen(
    seasonDetails: SeasonDetails,
    onEpisodeClick: (Episode) -> Unit
)
{
    Scaffold(
        topBar = { TopBar(title = seasonDetails.name) }
    )
    { innerPadding ->
        EpisodeList(episodes = seasonDetails.episodes, modifier = Modifier.padding(innerPadding), onEpisodeClick )
    }
}

@Composable
fun EpisodeList(
    episodes: List<Episode>,
    modifier: Modifier = Modifier,
    onEpisodeClick: (Episode) -> Unit
)
{
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    )
    {
        items(items = episodes)
        {
            EpisodeItem(episode = it, onEpisodeClick)
        }
    }
}

@Composable
fun EpisodeItem(episode: Episode, onEpisodeClick: (Episode) -> Unit)
{
    Card(onClick = { onEpisodeClick(episode) }) {
        Row(Modifier.height(100.dp)) {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(100.dp)
            )
            {
                Image(url = episode.stillUrl!!, modifier = Modifier.fillMaxSize())

                Rank(
                    rank = episode.episodeNumber, modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.TopStart)
                        .padding(start = 2.dp, top = 2.dp)
                )

                ScoreBar(
                    score = episode.voteAverage,
                    modifier =
                    Modifier
                        //.size(40.dp)
                        .align(Alignment.BottomStart))
            }

            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = episode.episodeNumber.toString() + ". " + episode.name)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = episode.overview, maxLines = 3)
            }
        }

    }
}

//@Preview
@Composable
fun PreviewEpisodeItem()
{
    EpisodeItem(episode = episode, {})
}

//@Preview
@Composable
fun PreviewEpisodeList()
{
    EpisodeList(episodes = episodes, onEpisodeClick = {})
}

@Preview
@Composable
fun PreviewSeasonDetailsScreen()
{
    TMDBUnofficialTheme { SeasonDetailsScreen(seasonDetails, {}) }
}