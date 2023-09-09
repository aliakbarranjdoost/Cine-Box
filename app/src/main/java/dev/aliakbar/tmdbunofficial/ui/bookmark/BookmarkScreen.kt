package dev.aliakbar.tmdbunofficial.ui.bookmark

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.aliakbar.tmdbunofficial.ui.details.DetailsViewModel

@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel = viewModel(factory = DetailsViewModel.factory)
)
{

}