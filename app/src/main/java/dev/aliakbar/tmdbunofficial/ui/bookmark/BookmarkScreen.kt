package dev.aliakbar.tmdbunofficial.ui.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Bookmark
import dev.aliakbar.tmdbunofficial.data.source.sample.bookmarks
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicator
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.theme.TMDBUnofficialTheme
import dev.aliakbar.tmdbunofficial.util.calculateBackdropHeight
import kotlin.math.roundToInt

@Composable
fun BookmarkScreen(
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToTv: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BookmarkViewModel = hiltViewModel()
)
{
    when (val uiState = viewModel.bookmarkUiState.collectAsStateWithLifecycle().value)
    {
        is BookmarkUiState.Loading -> CircularIndicator()
        is BookmarkUiState.Success ->
        {
            BookmarkList(
                bookmarks = uiState.bookmarks,
                onNavigateToMovie = onNavigateToMovie,
                onNavigateToTv = onNavigateToTv
            )
        }

        is BookmarkUiState.Error   -> Text(text = "Error")
    }
}

@Composable
fun BookmarkList(
    bookmarks: List<Bookmark>,
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToTv: (Int) -> Unit,
    modifier: Modifier = Modifier
)
{
    LazyColumn(
        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_large)),
        contentPadding = PaddingValues(vertical = dimensionResource(R.dimen.padding_large)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    )
    {
        items(items = bookmarks)
        { trend ->
            BookmarkItem(
                bookmark = trend,
                onNavigate =
                {
                    val trendId = trend.id
                    when (trend.type)
                    {
                        "movie" -> onNavigateToMovie(trendId)
                        else    -> onNavigateToTv(trendId)
                    }
                }
            )
        }
    }
}

@Composable
fun BookmarkItem(
    bookmark: Bookmark,
    onNavigate: (Int) -> Unit,
    modifier: Modifier = Modifier
)
{
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val backdropHeight = calculateBackdropHeight(screenWidth)

    val colorStops = listOf(
        Color.Black.copy(alpha = 0.0f),
        Color.Black.copy(alpha = 0.8f)
    )
    Card(
        onClick = { onNavigate(bookmark.id) },
        modifier = Modifier
            .fillMaxWidth()
            .height(backdropHeight.dp),
        shape = CardDefaults.shape
    )
    {
        Box(
            modifier = Modifier.fillMaxSize()
        )
        {
            Image(
                url = bookmark.backdropUrl,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(colors = colorStops))
            )

            Text(
                text = bookmark.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_large))
                    .align(Alignment.BottomStart)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookmarkScreenPreview()
{
    TMDBUnofficialTheme()
    {
        BookmarkList(bookmarks = bookmarks, {}, {})
    }
}