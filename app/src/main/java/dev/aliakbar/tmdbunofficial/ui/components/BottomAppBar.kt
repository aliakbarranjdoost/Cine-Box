package dev.aliakbar.tmdbunofficial.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import dev.aliakbar.tmdbunofficial.Bookmark
import dev.aliakbar.tmdbunofficial.Home
import dev.aliakbar.tmdbunofficial.Search
import dev.aliakbar.tmdbunofficial.Setting
import dev.aliakbar.tmdbunofficial.TmdbDestination
import dev.aliakbar.tmdbunofficial.Top

@Composable
fun TmdbBottomAppBar(
    allScreens: List<TmdbDestination>,
    selected: (TmdbDestination) -> Boolean,
    onTabSelected: (TmdbDestination) -> Unit,
)
{
    NavigationBar()
    {
        allScreens.forEach()
        { screen ->
            val icon = selectIcon(screen)

            NavigationBarItem(
                icon = {
                    if (selected(screen))
                        Icon(icon, contentDescription = null)
                    else
                        Icon(screen.icon, contentDescription = null)
                },
                label = { Text(screen.route, fontSize = 11.sp) },
                selected = selected(screen),
                onClick = { onTabSelected(screen) }
            )
        }
    }
}

private fun selectIcon(screen: TmdbDestination): ImageVector = when(screen)
{
    is Home -> Icons.Filled.Home
    is Bookmark -> Icons.Filled.Bookmarks
    is Search -> Icons.Filled.Search
    is Top -> Icons.AutoMirrored.Filled.List
    is Setting -> Icons.Filled.Settings
    else -> Icons.Filled.Home
}