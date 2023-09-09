@file:OptIn(ExperimentalMaterial3Api::class)

package dev.aliakbar.tmdbunofficial.ui.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.data.source.sample.recommendations
import dev.aliakbar.tmdbunofficial.ui.details.DetailsViewModel

@Composable
fun BookmarkScreen(
    navController: NavHostController,
    viewModel: BookmarkViewModel = viewModel(factory = DetailsViewModel.factory)
)
{
    var uiState = viewModel.bookmarkUiState

    when (uiState)
    {
        is BookmarkUiState.Loading -> Text(text = "Loading")
        is BookmarkUiState.Success -> BookmarkList(bookmarks = uiState.bookmarks)
        is BookmarkUiState.Error   -> Text(text = "Error")
    }
}

@Composable
fun BookmarkList(bookmarks: List<Trend>, modifier: Modifier = Modifier)
{
    LazyColumn()
    {
        items(items = bookmarks)
        {
            trend ->
            BookmarkItem(bookmark = trend)
        }
    }
}

@Composable
fun BookmarkItem(bookmark: Trend, modifier: Modifier = Modifier)
{
    Card(modifier = Modifier.padding(16.dp))
    {
        Box()
        {
            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .data(bookmark.poster)
                    .build(),
                placeholder = painterResource(id = R.drawable.poster_test),
                contentDescription = bookmark.title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(width = 160.dp, height = 320.dp).align(Alignment.TopStart)
            )
            Text(
                text = bookmark.title,
                modifier = Modifier.align(Alignment.TopCenter).padding(start = 160.dp)
            )

            IconButton(onClick = { /* doSomething() */ },
                modifier = Modifier.align(Alignment.BottomEnd).padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.BookmarkBorder,
                    contentDescription = "Localized description"
                )
            }
        }
    }
}


@Preview
@Composable
fun BookmarkScreenPreview()
{
    BookmarkList(bookmarks = recommendations)
}