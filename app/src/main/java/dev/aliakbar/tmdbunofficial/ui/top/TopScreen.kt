package dev.aliakbar.tmdbunofficial.ui.top

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import dev.aliakbar.tmdbunofficial.ui.home.HomeViewModel

@Composable
fun TopScreen(
    navController: NavHostController,
    viewModel: TopViewModel = viewModel(factory = TopViewModel.factory),
    modifier: Modifier = Modifier
)
{

}