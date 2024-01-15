package dev.aliakbar.tmdbunofficial.ui.person

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PersonScreen(
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToTv: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PersonViewModel = viewModel(factory = PersonViewModel.factory)
)
{

}