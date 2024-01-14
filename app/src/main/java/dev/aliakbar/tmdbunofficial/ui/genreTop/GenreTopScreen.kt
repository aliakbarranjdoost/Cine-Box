package dev.aliakbar.tmdbunofficial.ui.genreTop

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.ui.top.TopList

@Composable
fun GenreTopScreen(
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToTv: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GenreTopViewModel = viewModel(factory = GenreTopViewModel.factory)
)
{
    val movies = viewModel.result.collectAsLazyPagingItems()
//    val genre = viewModel.genre.collectAsStateWithLifecycle()
    
    TopList(tops = movies, onNavigate = {}, addToBookmark = {}, removeFromBookmark = {})
}

@Composable
fun MoviesList(movies: List<Trend>, modifier: Modifier = Modifier)
{
    
}