package dev.aliakbar.tmdbunofficial.ui.episode

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SeasonDetailsScreen(
    viewModel: EpisodeListViewModel = viewModel(factory = EpisodeListViewModel.factory)
)
{
    val uiState = viewModel.episodeListUiState

    when(uiState)
    {
        is SeasonDetailsUiState.Loading -> Text(text = "Loading")
        is SeasonDetailsUiState.Success -> Text(text = uiState.seasonDetails.id.toString())
        is SeasonDetailsUiState.Error   -> Text(text = "Error")
    }
}