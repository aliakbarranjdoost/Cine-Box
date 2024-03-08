package dev.aliakbar.tmdbunofficial.ui.genreTop

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.ui.components.TopBar
import dev.aliakbar.tmdbunofficial.ui.top.TopList

@Composable
fun GenreTopScreen(
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToTv: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GenreTopViewModel = hiltViewModel()
)
{
    val movies = viewModel.result.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(
                    id = R.string.popular_of_genre,
                    viewModel.genreName,
                    stringResource(if (viewModel.type)  R.string.movies else R.string.tvs)
                ),
                onNavigateBack = onNavigateBack,
            )
        },
    )
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
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top =  innerPadding.calculateTopPadding())
        )
    }
}