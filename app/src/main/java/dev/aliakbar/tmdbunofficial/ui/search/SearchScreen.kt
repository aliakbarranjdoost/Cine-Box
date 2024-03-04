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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.MediaType
import dev.aliakbar.tmdbunofficial.data.SearchResult
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicatorLoadMore
import dev.aliakbar.tmdbunofficial.ui.components.Image

private val TAG: String = "Search"

@Composable
fun SearchScreen(
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToTv: (Int) -> Unit,
    onNavigateToPerson: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
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
                viewModel.setQuery(text)
                viewModel.search()
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
                        IconButton(onClick = { viewModel.setQuery("") })
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

        LazyColumn(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp , top = 72.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            items(searchResult.itemCount)
            { index ->
                searchResult[index]?.let()
                { searchResult ->
                    SearchResultItem(
                        result = searchResult,
                        onNavigate =
                        {
                            val searchResultId = searchResult.id
                            when (searchResult.mediaType)
                            {
                                MediaType.MOVIE -> onNavigateToMovie(searchResultId)
                                MediaType.TV -> onNavigateToTv(searchResultId)
                                MediaType.PERSON -> onNavigateToPerson(searchResultId)
                            }
                        }
                    )
                }
            }
            item()
            {
                CircularIndicatorLoadMore(modifier = Modifier.fillMaxWidth().height(40.dp))
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
fun SearchResultItem(
    result: SearchResult,
    onNavigate: () -> Unit
)
{
    ElevatedCard(
        onClick = onNavigate,
        modifier = Modifier.fillMaxWidth(),
    )
    {
        Row()
        {
            Poster(
                posterPath = result.posterUrl,
                contentDescription = result.title,
            )

            Box(modifier = Modifier.height(150.dp))
            {
                Text(
                    text = result.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopStart)
                )

                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomStart)
                ) {
                    Text(
                        text = if (result.mediaType != MediaType.PERSON) result.mediaType.toString()
                        else result.knownForDepartment!!,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .weight(0.3f)
                    )

                    if (result.mediaType == MediaType.MOVIE || result.mediaType == MediaType.TV)
                    {
                        if (!result.releaseDate.isNullOrEmpty())
                        {
                            result.releaseDate.substring(0..3).let()
                            {
                                Text(
                                    text = it,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier
                                        .weight(0.3f)
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.weight(0.3f)

                        ) {
                            Text(
                                text = "%.${1}f".format(result.score),
                                fontWeight = FontWeight.Medium,
                            )
                            Text(
                                text = "/10",
                                fontWeight = FontWeight.Normal,
                            )
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = Color.Yellow,
                                modifier = Modifier.weight(0.1f)
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
    Image(url = posterPath, modifier = Modifier.size(width = 100.dp, height = 150.dp))
}