package dev.aliakbar.tmdbunofficial.ui.episode

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.aliakbar.tmdbunofficial.ui.details.DetailsViewModel

@Composable
fun EpisodeListScreen(
    viewModel: EpisodeListViewModel = viewModel(factory = EpisodeListViewModel.factory)
)
{
    val uiState = viewModel.episodeListUiState

    when(uiState)
    {
        is EpisodeListUiState.Loading -> Text(text = "Loading")
        is EpisodeListUiState.Success -> Text(text = uiState.seasonDetails.id.toString())
        is EpisodeListUiState.Error -> Text(text = "Error")
    }
}