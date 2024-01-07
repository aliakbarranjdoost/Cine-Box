package dev.aliakbar.tmdbunofficial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.aliakbar.tmdbunofficial.ui.bookmark.BookmarkScreen
import dev.aliakbar.tmdbunofficial.ui.details.DetailsScreen
import dev.aliakbar.tmdbunofficial.ui.episode.EpisodeScreen
import dev.aliakbar.tmdbunofficial.ui.home.HomeScreen
import dev.aliakbar.tmdbunofficial.ui.search.SearchScreen
import dev.aliakbar.tmdbunofficial.ui.season.SeasonDetailsScreen
import dev.aliakbar.tmdbunofficial.ui.theme.TMDBUnofficialTheme
import dev.aliakbar.tmdbunofficial.ui.top.TopScreen

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent()
        {
            TMDBUnofficialTheme(darkTheme = false, dynamicColor = false)
            {
                TmdbApp()
            }
        }
    }
}

@Composable
fun TmdbApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
)
{
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        bottomBar =
        {
            TmdbBottomAppBar(
                allScreens = bottomNavigationItems,
                selected = { currentDestination?.hierarchy?.any { it.route == it.route } == true },
                onTabSelected =
                {
                    navController.navigate(it.route)
                    {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id)
                        {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    )
    { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Home.route,
            modifier = modifier.padding(innerPadding)
        )
        {
            composable(route = Home.route)
            {
                HomeScreen(navController)
            }
            composable(route = Bookmark.route)
            {
                BookmarkScreen(navController = navController)
            }
            composable(route = Season.route)
            {
                SearchScreen(navController = navController)
            }
            composable(route = Top.route)
            {
                TopScreen(navController)
            }
            composable(route = Search.route)
            {
                Text(text = stringResource(id = R.string.setting))
            }
            composable(
                route = Movie.routeWithArgs,
                arguments = Movie.arguments
            )
            {
                // TODO: change this screen name to Movie
                DetailsScreen(navController)
            }
            composable(
                route = Tv.routeWithArgs,
                arguments = Tv.arguments
            )
            {
                // TODO: Separate Movie and tv screen
                // Tv()
            }
            composable(
                route = Season.routeWithArgs,
                arguments = Season.arguments
            )
            {
                SeasonDetailsScreen(navController)
            }
            composable(
                route = Episode.routeWithArgs,
                arguments = Episode.arguments
            )
            {
                EpisodeScreen()
            }
        }
    }
}

@Composable
fun TmdbBottomAppBar(
    allScreens: List<TmdbDestination>,
    selected: (TmdbDestination) -> Boolean,
    onTabSelected: (TmdbDestination) -> Unit,
)
{
    NavigationBar()
    {
        bottomNavigationItems.forEach()
        { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.route, fontSize = 11.sp) },
//                selected = currentDestination.hierarchy?.any { it.route == screen.route } == true,
                selected = selected(screen),
                onClick = { onTabSelected(screen) }
/*
                {
                    navController.navigate(screen.name)
                    {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id)
                        {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
*/
            )
        }
    }
}