package dev.aliakbar.tmdbunofficial.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import dev.aliakbar.tmdbunofficial.Bookmark
import dev.aliakbar.tmdbunofficial.Home
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.Search
import dev.aliakbar.tmdbunofficial.Setting
import dev.aliakbar.tmdbunofficial.TmdbDestination
import dev.aliakbar.tmdbunofficial.TmdbMainDestination
import dev.aliakbar.tmdbunofficial.Top

@Composable
fun TmdbBottomAppBar(
    allScreens: List<TmdbMainDestination>,
    selected: (TmdbDestination) -> Boolean,
    onTabSelected: (TmdbDestination) -> Unit,
)
{
    NavigationBar()
    {
        allScreens.forEach()
        { screen ->

            NavigationBarItem(
                icon =
                {
                    Icon(
                        painter = painterResource(
                            id =
                            if (selected(screen)) screen.selectedIcon
                            else screen.defaultIcon
                        ),
                        contentDescription = null
                    )
                },
                label = { Text(screen.route, fontSize = 11.sp) },
                selected = selected(screen),
                onClick = { onTabSelected(screen) }
            )
        }
    }
}

@Composable
private fun selectIcon(screen: TmdbDestination): Int = when(screen)
{
    is Home -> R.drawable.ic_baseline_home
    is Bookmark -> R.drawable.ic_baseline_bookmarks
    is Search -> R.drawable.ic_search
    is Top -> R.drawable.ic_list
    is Setting -> R.drawable.ic_baseline_settings
    else -> 0
}