package dev.aliakbar.tmdbunofficial.ui.season

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.aliakbar.tmdbunofficial.data.Episode
import dev.aliakbar.tmdbunofficial.data.SeasonDetails
import dev.aliakbar.tmdbunofficial.data.source.sample.episode
import dev.aliakbar.tmdbunofficial.data.source.sample.episodes
import dev.aliakbar.tmdbunofficial.data.source.sample.seasonDetails
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicator
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.components.TopBar
import dev.aliakbar.tmdbunofficial.ui.theme.TMDBUnofficialTheme

@Composable
fun SeasonScreen(
    onNavigateToEpisode: (Int,Int,Int) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: SeasonViewModel = hiltViewModel()
)
{
    when (val uiState = viewModel.episodeListUiState)
    {
        is SeasonUiState.Loading -> CircularIndicator()
        is SeasonUiState.Success ->
        {
            SeasonScreen(
                seasonDetails = uiState.seasonDetails,
                onNavigateToEpisode =
                { seasonNumber, episodeNumber ->
                    onNavigateToEpisode(viewModel.id, seasonNumber, episodeNumber)
                },
                onNavigateBack = onNavigateBack
            )
        }

        is SeasonUiState.Error   -> Text(text = "Error")
    }
}

@Composable
fun SeasonScreen(
    seasonDetails: SeasonDetails,
    onNavigateToEpisode: (Int, Int) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
)
{
    Scaffold(
        topBar = { TopBar(title = seasonDetails.name, onNavigateBack = onNavigateBack) },
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    { innerPadding ->
        EpisodeList(
            episodes = seasonDetails.episodes,
            modifier = Modifier.padding(innerPadding),
            onNavigateToEpisode = onNavigateToEpisode
        )
    }
}

@Composable
fun EpisodeList(
    episodes: List<Episode>,
    onNavigateToEpisode: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
)
{
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    )
    {
        items(items = episodes)
        {
            EpisodeItem(episode = it, onNavigateToEpisode = onNavigateToEpisode)
        }
    }
}

@Composable
fun EpisodeItem(
    episode: Episode,
    onNavigateToEpisode: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
)
{
    val colorStops = listOf(
        Color.Black.copy(alpha = 0.3f),
        Color.Black.copy(alpha = 0.0f)
    )

    Card(
        onClick = { onNavigateToEpisode(episode.seasonNumber, episode.episodeNumber) },
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
    ) {
        Row()
        {
            Box(
                modifier = Modifier.size(width = 150.dp, height = 100.dp)
            )
            {
                Image(url = episode.stillUrl!!, modifier = Modifier.size(width = 150.dp, height = 100.dp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(colors = colorStops))
                )
                Text(
                    text = episode.episodeNumber.toString(),
                    maxLines = 1,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopStart),
                )
            }

            Column {
                Text(text = episode.name, modifier = Modifier.padding(8.dp))

                Spacer(modifier = Modifier.fillMaxWidth().weight(1f))

                if (episode.voteAverage > 0)
                {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    )
                    {
                        Text(
                            text = "%.${1}f".format(episode.voteAverage),
                            fontWeight = FontWeight.Medium,
                        )
                        Text(
                            text = "/10",
                            fontWeight = FontWeight.Normal,
                        )
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color.Yellow,
                        )
                    }
                }
            }


        }
    }
}

@Preview
@Composable
fun PreviewSeasonDetailsScreen()
{
    TMDBUnofficialTheme()
    {
        SeasonScreen(seasonDetails = seasonDetails, onNavigateToEpisode = { _, _ -> }, onNavigateBack = {})
    }
}