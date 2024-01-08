@file:OptIn(
    ExperimentalMaterial3Api::class
)

package dev.aliakbar.tmdbunofficial.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.MediaType
import dev.aliakbar.tmdbunofficial.data.SearchResult
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicator
import dev.aliakbar.tmdbunofficial.ui.components.Image

private val TAG: String = "Search"

@Composable
fun SearchScreen(
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToTv: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.factory)
)
{
    val searchResult = viewModel.resultPager.collectAsLazyPagingItems()

    val text by viewModel.query.collectAsStateWithLifecycle()
    var active by rememberSaveable { mutableStateOf(false) }
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(Modifier.fillMaxSize())
    {
        SearchBar(
            modifier = Modifier.align(Alignment.TopCenter),
            query = text,
            onQueryChange = { viewModel.setQuery(it) },
            onSearch = {
                //viewModel.invalidateDataSource()
                viewModel.setQuery(text)
                active = false
            },
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
                        IconButton(onClick = { viewModel.setQuery( "" ) })
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

        when (searchResult.loadState.refresh)
        {
            is LoadState.Loading -> CircularIndicator()
            is LoadState.Error -> Text(text = "Error")

            else ->
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
                        { searchResult->
                            CategoryItem(
                                result = searchResult,
                                onNavigate =
                                {
                                    val searchResultId = searchResult.id
                                    when (searchResult.mediaType)
                                    {
                                        MediaType.MOVIE   -> onNavigateToMovie(searchResultId)
                                        MediaType.TV -> onNavigateToTv(searchResultId)
                                        MediaType.PERSON -> { }
                                    }
                                },
                                addToBookmark =  { viewModel.addToBookmark(it)},
                                removeFromBookmark = {viewModel.removeFromBookmark(it)}
                            )
                        }
                    }
                }
            }
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

@Composable
fun CategoryItem(
    result: SearchResult,
    onNavigate: () -> Unit,
    addToBookmark: (SearchResult) -> Unit,
    removeFromBookmark: (SearchResult) -> Unit,
)
{
    var isBookmark by remember { mutableStateOf(result.isBookmark) }

    Card(onClick = onNavigate)
    {
        Row(modifier = Modifier.fillMaxSize())
        {
            Poster(
                posterPath = result.posterUrl,
                contentDescription = result.title,
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

                    if (result.mediaType.name != MediaType.PERSON.name)
                    {
                        IconButton(
                            onClick = {
                                if (isBookmark)
                                {
                                    removeFromBookmark(result)
                                }
                                else
                                {
                                    addToBookmark(result)
                                }

                                isBookmark = !isBookmark
                            },
                            modifier = Modifier.size(48.dp)
                        )
                        {
                            Icon(
                                imageVector = if (isBookmark) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = null,
                                tint = if (isBookmark) Color.Yellow else LocalContentColor.current,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Poster(
    posterPath: String,
    contentDescription: String,
    modifier: Modifier = Modifier
)
{
    Box(modifier = Modifier.size(width = 100.dp, height = 150.dp))
    {
        Image(url = posterPath, modifier = Modifier.fillMaxSize())
    }
}