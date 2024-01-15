package dev.aliakbar.tmdbunofficial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.aliakbar.tmdbunofficial.ui.bookmark.BookmarkScreen
import dev.aliakbar.tmdbunofficial.ui.components.TmdbBottomAppBar
import dev.aliakbar.tmdbunofficial.ui.episode.EpisodeScreen
import dev.aliakbar.tmdbunofficial.ui.genreTop.GenreTopScreen
import dev.aliakbar.tmdbunofficial.ui.home.HomeScreen
import dev.aliakbar.tmdbunofficial.ui.movie.MovieScreen
import dev.aliakbar.tmdbunofficial.ui.person.PersonScreen
import dev.aliakbar.tmdbunofficial.ui.search.SearchScreen
import dev.aliakbar.tmdbunofficial.ui.season.SeasonScreen
import dev.aliakbar.tmdbunofficial.ui.theme.TMDBUnofficialTheme
import dev.aliakbar.tmdbunofficial.ui.top.TopScreen
import dev.aliakbar.tmdbunofficial.ui.tv.TvScreen

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
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
)
{
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        bottomBar =
        {
            TmdbBottomAppBar(
                allScreens = bottomNavigationItems,
                selected = { screen -> currentDestination?.hierarchy?.any { it.route == screen.route } == true },
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
        TmdbNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun TmdbNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
)
{
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = modifier
    )
    {
        composable(route = Home.route)
        {
            HomeScreen(
                onNavigateToMovie = { navController.navigateToMovie(it) },
                onNavigateToTv = { navController.navigateToTv(it) }
            )
        }
        composable(route = Bookmark.route)
        {
            BookmarkScreen(
                onNavigateToMovie = { navController.navigateToMovie(it) },
                onNavigateToTv = { navController.navigateToTv(it) }
            )
        }
        composable(route = Search.route)
        {
            SearchScreen(
                onNavigateToMovie = { navController.navigateToMovie(it) },
                onNavigateToTv = { navController.navigateToTv(it) },
            )
        }
        composable(route = Top.route)
        {
            TopScreen(
                onNavigateToMovie = { navController.navigateToMovie(it) },
                onNavigateToTv = { navController.navigateToTv(it) },
            )
        }
        composable(route = Setting.route)
        {
            Text(text = stringResource(id = R.string.setting))
        }
        composable(
            route = Movie.routeWithArgs,
            arguments = Movie.arguments
        )
        {
            MovieScreen(
                onNavigateToMovie = { navController.navigateToMovie(it) },
                onNavigateToPerson = { navController.navigateToPerson(it) },
                onNavigateBack = { navController.navigateUp() },
                onNavigateToGenreTop = { genreId, genreName, type ->
                    navController.navigateToGenreTop(genreId,genreName, type)
                }
            )
        }
        composable(
            route = Tv.routeWithArgs,
            arguments = Tv.arguments
        )
        {
            TvScreen(
                onNavigateToTv = { navController.navigateToTv(it) },
                onNavigateToSeason = { tvId, seasonNumber ->
                    navController.navigateToSeason(tvId, seasonNumber)
                },
                onNavigateToPerson = { navController.navigateToPerson(it) },
                onNavigateToGenreTop = { genreId, genreName, type ->
                    navController.navigateToGenreTop(genreId,genreName, type)
                },
                onNavigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = Season.routeWithArgs,
            arguments = Season.arguments
        )
        {
            SeasonScreen(
                { tvId, seasonNumber, episodeNumber ->
                    navController.navigateToEpisode(tvId, seasonNumber, episodeNumber)
                },
                onNavigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = Episode.routeWithArgs,
            arguments = Episode.arguments
        )
        {
            EpisodeScreen(
                onNavigateToPerson = { navController.navigateToPerson(it) },
                onNavigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = GenreTop.routeWithArgs,
            arguments = GenreTop.arguments
        )
        {
            GenreTopScreen(
                onNavigateToMovie = { navController.navigateToMovie(it) },
                onNavigateToTv = { navController.navigateToTv(it) },
                onNavigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = Person.routeWithArg,
            arguments = Person.arguments
        )
        {
            PersonScreen(
                onNavigateToMovie = { navController.navigateToMovie(it) },
                onNavigateToTv = { navController.navigateToTv(it) },
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}

fun NavHostController.navigateToMovie(id: Int)
{
    this.navigate("${Movie.route}/$id")
}

fun NavHostController.navigateToTv(id: Int)
{
    this.navigate("${Tv.route}/$id")
}

fun NavHostController.navigateToSeason(id: Int, seasonNumber: Int)
{
    this.navigate("${Season.route}/$id/$seasonNumber")
}

fun NavHostController.navigateToEpisode(id: Int, seasonNumber: Int, episodeNumber: Int)
{
    this.navigate("${Episode.route}/$id/$seasonNumber/$episodeNumber")
}

fun NavHostController.navigateToGenreTop(genreId: Int,genreName: String, type: Boolean)
{
    this.navigate("${GenreTop.route}/$genreId/$genreName/$type")
}

fun NavHostController.navigateToPerson(id: Int)
{
    this.navigate("${Person.route}/$id")
}