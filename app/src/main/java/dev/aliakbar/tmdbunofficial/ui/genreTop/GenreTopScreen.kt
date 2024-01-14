package dev.aliakbar.tmdbunofficial.ui.genreTop

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.ui.components.TopBar
import dev.aliakbar.tmdbunofficial.ui.top.TopList

@Composable
fun GenreTopScreen(
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToTv: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GenreTopViewModel = viewModel(factory = GenreTopViewModel.factory)
)
{
    val movies = viewModel.result.collectAsLazyPagingItems()
//    val genre = viewModel.genre.collectAsStateWithLifecycle()

    Scaffold(topBar = { TopBar(title = viewModel.genreName, onNavigateBack = onNavigateBack) })
    { innerPadding ->
        TopList(
            tops = movies,
            onNavigate =
            {
                if (viewModel.type)
                {
                    onNavigateToMovie(it)
                }
                else
                {
                    onNavigateToTv(it)
                }
            },
            addToBookmark = {},
            removeFromBookmark = {},
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top =  innerPadding.calculateTopPadding() + 16.dp)
        )
    }
}

@Composable
fun MoviesList(movies: List<Trend>, modifier: Modifier = Modifier)
{
    
}