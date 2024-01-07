package dev.aliakbar.tmdbunofficial.ui.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Bookmark
import dev.aliakbar.tmdbunofficial.data.MediaType
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.data.source.sample.bookmarks
import dev.aliakbar.tmdbunofficial.data.source.sample.trend
import dev.aliakbar.tmdbunofficial.ui.theme.TMDBUnofficialTheme

@Composable
fun BookmarkScreen(
    navController: NavHostController,
    viewModel: BookmarkViewModel = viewModel(factory = BookmarkViewModel.factory)
)
{
    when (val uiState = viewModel.bookmarkUiState.collectAsStateWithLifecycle().value)
    {
        is BookmarkUiState.Loading -> Text(text = "Loading")
        is BookmarkUiState.Success -> BookmarkList(bookmarks = uiState.bookmarks, navController,
            removeBookmark = { viewModel.removeFromBookmark(it) })
        is BookmarkUiState.Error   -> Text(text = "Error")
    }
}

@Composable
fun BookmarkList(bookmarks: List<Bookmark>,navController: NavHostController,
                 removeBookmark: (Bookmark) -> Unit,
                 modifier: Modifier = Modifier)
{
    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        items(items = bookmarks)
        { trend ->
            BookmarkItem(
                bookmark = trend,
                onNavigateToDetails =
                {
                    if (trend.type == "movie")
                    {
                        //navController.navigate(TmdbScreen.MovieDetails.name + "/" + trend.id.toString() + "/true")
                    }
                    else if (trend.type == "tv")
                    {
                        //navController.navigate(TmdbScreen.MovieDetails.name + "/" + trend.id.toString() + "/false")
                    }
                },
                removeBookmark = removeBookmark
            )
        }
    }
}

@Composable
fun BookmarkItem(bookmark: Bookmark, onNavigateToDetails: () -> Unit,
                 removeBookmark: (Bookmark) -> Unit,
                 modifier: Modifier = Modifier)
{
    Card(
        onClick = onNavigateToDetails,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = CardDefaults.shape
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
        )
        {
            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .data(bookmark.poster)
                    .build(),
                placeholder = painterResource(id = R.drawable.backdrop_test),
                contentDescription = bookmark.title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .weight(0.76F)
                    .fillMaxWidth()
            )

            Text(
                text = bookmark.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(0.12F)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.headlineSmall
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .weight(0.12F)
                    .fillMaxWidth()
            )
            {
                Text(
                    text = bookmark.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f),
                    style = MaterialTheme.typography.titleSmall
                )

                IconButton(
                    onClick = { removeBookmark(bookmark) },
                    modifier = Modifier
                        .size(48.dp)
                        .padding(4.dp)
                )
                {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        tint = Color.Yellow,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookmarkScreenPreview()
{
    TMDBUnofficialTheme()
    {
        BookmarkList(bookmarks = bookmarks, rememberNavController(), {})
    }
}