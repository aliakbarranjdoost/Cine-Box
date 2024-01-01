package dev.aliakbar.tmdbunofficial.ui.episode

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.aliakbar.tmdbunofficial.data.Episode
import dev.aliakbar.tmdbunofficial.data.EpisodeDetails
import dev.aliakbar.tmdbunofficial.ui.components.TopBar
import dev.aliakbar.tmdbunofficial.ui.home.HomeViewModel
import dev.aliakbar.tmdbunofficial.ui.season.EpisodeList

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
    Scaffold(
        topBar = { TopBar(title = episode.name) }
    )
    { innerPadding ->

    }
}