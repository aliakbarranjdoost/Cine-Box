@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)

package dev.aliakbar.tmdbunofficial.ui.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.MediaType
import dev.aliakbar.tmdbunofficial.data.SearchResult
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.data.source.sample.recommendations
import dev.aliakbar.tmdbunofficial.ui.home.ScoreBar
import dev.aliakbar.tmdbunofficial.ui.theme.TMDBUnofficialTheme

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.factory),
    modifier: Modifier = Modifier
)
{
    var uiState = viewModel.searchUiState
    var searchResult = viewModel.search("lord").collectAsLazyPagingItems()

    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(Modifier.fillMaxSize())
    {
        SearchBar(
            modifier = Modifier.align(Alignment.TopCenter),
            query = text,
            onQueryChange = { text = it },
            onSearch = { active = false },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text(stringResource(id = R.string.search)) },
            leadingIcon =
            {
                if (active)
                {
                    IconButton(onClick = { active = false })
                    {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                }
                else
                {
                    Icon(Icons.Default.Search, contentDescription = null)
                }
            },
            trailingIcon =
            {
                Row()
                {
                    SearchMenu(expanded = expanded, onDismissRequest = { expanded = false })

                    if (active && text.isNotEmpty())
                    {
                        IconButton(onClick = { text = "" })
                        {
                            Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                        }
                    }

                    if (active)
                    {
                        IconButton(onClick = { expanded = true })
                        {
                            Icon(Icons.Default.MoreVert, contentDescription = null)
                        }
                    }
                }
            },
        )
        {
            // TODO: Find a way to show suggestion
            /*repeat(4)
            { idx ->
                val resultText = "Suggestion $idx"
                ListItem(
                    headlineContent = { Text(resultText) },
                    supportingContent = { Text("Additional info") },
                    leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                    modifier = Modifier
                        .clickable {
                            text = resultText
                            active = false
                        }
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }*/
        }

        when (uiState)
        {
            is SearchUiState.Loading -> Text(text = "Loading")
            is SearchUiState.Success ->
            {
                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(top = 56.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                )
                {
                    items(searchResult.itemCount)
                    { index ->
                        searchResult[index]?.let()
                        {
                            CategoryItem(result = it, onNavigateToDetails = { /*TODO*/ }) {}
                        }
                    }
                }
            }

            is SearchUiState.Error   -> Text(text = "Error")
        }

    }
}

@Composable
fun SearchMenu(expanded: Boolean, onDismissRequest: () -> Unit, modifier: Modifier = Modifier)
{
    Box()
    {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest
        )
        {
            DropdownMenuItem(
                text = { Text(text = "Advance Search") },
                onClick = { },
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) }
            )
        }
    }
}

data class Category<T>(
    val name: String,
    val items: List<T>
)

@Composable
fun CategorizedLazyColumn(
    categories: List<Category<Trend>>,
    modifier: Modifier = Modifier
)
{
    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        categories.forEach()
        { category ->
            stickyHeader()
            {
                CategoryHeader(text = category.name)
            }

            items(category.items)
            {
                /*CategoryItem(trend = it, onNavigateToDetails = { *//*TODO*//* })
                {

                }*/
            }
        }
    }
}

@Composable
fun CategoryHeader(text: String, modifier: Modifier = Modifier)
{
    Text(text = text)
}

@Composable
fun CategoryItem(
    result: SearchResult,
    onNavigateToDetails: () -> Unit,
    onBookmarkClick: () -> Unit,
)
{
    Card(onClick = onNavigateToDetails)
    {
        Row(modifier = Modifier.fillMaxSize())
        {
            Poster(
                posterPath = result.posterUrl,
                contentDescription = result.title,
                onBookmarkClick = onBookmarkClick
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            {
                Text(
                    text = result.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.align(Alignment.BottomStart)
                ) {
                    Text(
                        text = if (result.mediaType != MediaType.PERSON) result.mediaType.toString()
                        else result.knownForDepartment!!,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(8.dp)
                    )

                    IconButton(
                        onClick = onBookmarkClick,
                        modifier = Modifier.size(48.dp)
                    )
                    {
                        Icon(
                            //imageVector = if (trend.isBookmark) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Poster(
    onBookmarkClick: () -> Unit,
    posterPath: String,
    contentDescription: String,
    modifier: Modifier = Modifier
)
{
    Box(modifier = Modifier.size(width = 100.dp, height = 150.dp))
    {
        AsyncImage(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .placeholder(R.drawable.poster_test)
                .data(posterPath)
                .build(),
            contentDescription = contentDescription,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        /*ScoreBar(
            score = posterPath.score,
            modifier = Modifier
                .align(Alignment.BottomStart)
        )*/
    }
}

@Preview
@Composable
fun PreviewCategorizedList()
{
    TMDBUnofficialTheme()
    {
        CategorizedLazyColumn(
            categories = listOf(
                Category("Movie", recommendations),
                Category("TVs", recommendations),
                Category("People", recommendations),
                Category("Collection", recommendations)
            )
        )
    }
}