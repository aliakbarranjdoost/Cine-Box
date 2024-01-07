package dev.aliakbar.tmdbunofficial.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import dev.aliakbar.tmdbunofficial.TmdbDestination

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
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.route, fontSize = 11.sp) },
                selected = selected(screen),
                onClick = { onTabSelected(screen) }
            )
        }
    }
}