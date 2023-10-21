package dev.aliakbar.tmdbunofficial.ui.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.factory),
    modifier: Modifier = Modifier
)
{

}