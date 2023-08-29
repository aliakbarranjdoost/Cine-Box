package dev.aliakbar.tmdbunofficial.ui.details

import android.telecom.Call.Details
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Movie
import dev.aliakbar.tmdbunofficial.ui.home.HomeViewModel

private const val TAG: String = "DetailsScreen"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(id: Int = 0,
                  viewModel: DetailsViewModel = viewModel(factory = DetailsViewModel.factory)
)
{
    viewModel.getMovieDetails(id = id)
    Log.d(TAG, "DetailsScreen: viewModel.getMovieDetails(id = $id)")
    val uiState = viewModel.detailsUiState

    when(uiState)
    {
        is DetailsUiState.Loading -> Text(text = "Loading")
        is DetailsUiState.Success -> ShowMovieDetails(movie = uiState.movie)
        is DetailsUiState.Error -> Text(text = "Error")
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowMovieDetails(movie: Movie)
{
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    "Centered TopAppBar",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Localized description"
                        )
                    }
                })
        }
    )
    { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding))
        {
            AsyncImage(
                model = painterResource(id = R.drawable.test),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Text(text = movie.title)
        }
    }
}