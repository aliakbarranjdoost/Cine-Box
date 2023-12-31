package dev.aliakbar.tmdbunofficial.ui.main

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.ui.bookmark.BookmarkScreen
import dev.aliakbar.tmdbunofficial.ui.details.DetailsScreen
import dev.aliakbar.tmdbunofficial.ui.episode.EpisodeScreen
import dev.aliakbar.tmdbunofficial.ui.season.SeasonDetailsScreen
import dev.aliakbar.tmdbunofficial.ui.home.HomeScreen
import dev.aliakbar.tmdbunofficial.ui.search.SearchScreen
import dev.aliakbar.tmdbunofficial.ui.top.TopScreen

enum class TmdbScreen(@StringRes val title: Int,val icon: ImageVector? = null)
{
    Splash(title = R.string.splash),
    Home(title = R.string.home,Icons.Filled.Home),
    Bookmark(title = R.string.bookmark,Icons.Filled.Favorite),
    Search(title = R.string.search,Icons.Filled.Search),
    Top(title = R.string.top, Icons.AutoMirrored.Filled.List),
    Setting(title = R.string.setting,Icons.Filled.Settings),
    MovieDetails(title = R.string.movie_details),
    SeriesDetails(title = R.string.series_details),
    EpisodeList(title = R.string.episode_list),
    EpisodeDetails(title = R.string.episode)
}

@Composable
fun TmdbApp(
    navController: NavHostController = rememberNavController()
)
{
    val backStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar =
        {
            TmdbBottomAppBar(navController = navController, backStackEntry = backStackEntry)
        }
    )
    { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TmdbScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        )
        {
            composable(route = TmdbScreen.Splash.name)
            {

            }
            composable(route = TmdbScreen.Home.name)
            {
                HomeScreen(navController)
            }
            composable(route = TmdbScreen.Bookmark.name)
            {
                BookmarkScreen(navController = navController)
            }
            composable(route = TmdbScreen.Search.name)
            {
                SearchScreen(navController = navController)
            }
            composable(route = TmdbScreen.Top.name)
            {
                TopScreen(navController)
            }
            composable(route = TmdbScreen.Setting.name)
            {
                Text(text = stringResource(id = TmdbScreen.Setting.title))
            }
            composable(
                route = TmdbScreen.MovieDetails.name + "/{id}" + "/{type}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType },
                    navArgument("type") { type = NavType.BoolType }
                )
                )
            { backStackEntry ->
                DetailsScreen(navController)
            }
            composable(route = TmdbScreen.SeriesDetails.name)
            {

            }
            composable(route = TmdbScreen.EpisodeList.name + "/{id}" + "/{seasonNumber}",
                arguments = listOf(
                    navArgument("id") {type = NavType.IntType},
                    navArgument("seasonNumber") {type = NavType.IntType}
                )
            )
            {
                SeasonDetailsScreen()
            }
            composable(
                route = TmdbScreen.EpisodeDetails.name + "episode",
                arguments = listOf(navArgument("episode") { type = NavType.StringType })
            )
            {
                EpisodeScreen()
            }
        }
    }
}

@Composable
fun TmdbBottomAppBar(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry?,
)
{
    val currentDestination = backStackEntry?.destination

    val bottomNavigationItems = listOf(
        TmdbScreen.Home,
        TmdbScreen.Bookmark,
        TmdbScreen.Search,
        TmdbScreen.Top,
        TmdbScreen.Setting
    )

    NavigationBar {

        bottomNavigationItems.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon!!, contentDescription = null) },
                label = { Text(stringResource(screen.title), fontSize = 11.sp) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.name } == true,
                onClick =
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
            )
        }
    }
}