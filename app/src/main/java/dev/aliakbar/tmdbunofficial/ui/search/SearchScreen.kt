@file:OptIn(ExperimentalMaterial3Api::class)

package dev.aliakbar.tmdbunofficial.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.ui.main.TmdbScreen

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.factory),
    modifier: Modifier = Modifier
)
{
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true })
    {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = -1f },
            query = text,
            onQueryChange = { text = it },
            onSearch = { active = false },
            active = active,
            onActiveChange = {
                active = it
            },
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
        ) {
            repeat(4) { idx ->
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
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, top = 72.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val list = List(100) { "Text $it" }
            items(count = list.size) {
                Text(
                    list[it],
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
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