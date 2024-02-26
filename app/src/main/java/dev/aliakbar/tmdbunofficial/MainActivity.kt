package dev.aliakbar.tmdbunofficial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aliakbar.tmdbunofficial.data.source.datastore.ThemeOptions
import dev.aliakbar.tmdbunofficial.ui.bookmark.BookmarkScreen
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicator
import dev.aliakbar.tmdbunofficial.ui.components.TmdbBottomAppBar
import dev.aliakbar.tmdbunofficial.ui.episode.EpisodeScreen
import dev.aliakbar.tmdbunofficial.ui.genreTop.GenreTopScreen
import dev.aliakbar.tmdbunofficial.ui.home.HomeScreen
import dev.aliakbar.tmdbunofficial.ui.movie.MovieScreen
import dev.aliakbar.tmdbunofficial.ui.person.PersonScreen
import dev.aliakbar.tmdbunofficial.ui.search.SearchScreen
import dev.aliakbar.tmdbunofficial.ui.season.SeasonScreen
import dev.aliakbar.tmdbunofficial.ui.setting.SettingScreen
import dev.aliakbar.tmdbunofficial.ui.setting.SettingsUiState
import dev.aliakbar.tmdbunofficial.ui.theme.TMDBUnofficialTheme
import dev.aliakbar.tmdbunofficial.ui.top.TopScreen
import dev.aliakbar.tmdbunofficial.ui.tv.TvScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        setContent()
        {
            val settingUiState by viewModel.settingsUiState.collectAsStateWithLifecycle()

            when (settingUiState)
            {
                is SettingsUiState.Loading -> CircularIndicator()

                is SettingsUiState.Success ->
                {
                    TMDBUnofficialTheme(
                        darkTheme = shouldUseDarkTheme(uiState = settingUiState),
                        dynamicColor = ((settingUiState as SettingsUiState.Success).settings.useDynamicColor))
                    {
                        TmdbApp()
                    }
                }
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
    var showBottomBar by rememberSaveable { (mutableStateOf(true)) }

    showBottomBar = when( currentDestination?.route)
    {
        Home.route, Bookmark.route, Search.route, Top.route, Setting.route -> true
        Movie.route, Tv.route, Season.route,Episode.route ,Person.route ,GenreTop.route -> false
        else -> false
    }

    Scaffold(
        bottomBar =
        {
            AnimatedVisibility (showBottomBar)
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
                                inclusive = true
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
                onNavigateToPerson = { navController.navigateToPerson(it) },
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
            SettingScreen()
        }
        composable(
            route = Movie.routeWithArgs,
            arguments = Movie.arguments
        )
        {
            MovieScreen(
                onNavigateToMovie = { navController.navigateFromMovieToMovie(it) },
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
                onNavigateToTv = { navController.navigateFromTvToTv(it) },
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

fun NavHostController.navigateSingleTop(route: String)
{
    this.navigate(route)
    {
        launchSingleTop = true
    }
}
fun NavHostController.navigateToMovie(id: Int)
{
    this.navigateSingleTop("${Movie.route}/$id")
}

fun NavHostController.navigateFromMovieToMovie(id: Int)
{
    this.navigate("${Movie.route}/$id")
}

fun NavHostController.navigateToTv(id: Int)
{
    this.navigateSingleTop("${Tv.route}/$id")
}

fun NavHostController.navigateFromTvToTv(id: Int)
{
    this.navigate("${Tv.route}/$id")
}

fun NavHostController.navigateToSeason(id: Int, seasonNumber: Int)
{
    this.navigateSingleTop("${Season.route}/$id/$seasonNumber")
}

fun NavHostController.navigateToEpisode(id: Int, seasonNumber: Int, episodeNumber: Int)
{
    this.navigateSingleTop("${Episode.route}/$id/$seasonNumber/$episodeNumber")
}

fun NavHostController.navigateToGenreTop(genreId: Int,genreName: String, type: Boolean)
{
    this.navigateSingleTop("${GenreTop.route}/$genreId/$genreName/$type")
}

fun NavHostController.navigateToPerson(id: Int)
{
    this.navigateSingleTop("${Person.route}/$id")
}

/**
 * Returns `true` if dark theme should be used, as a function of the [uiState] and the
 * current system context.
 */
@Composable
private fun shouldUseDarkTheme(
    uiState: SettingsUiState,
): Boolean = when (uiState)
{
    SettingsUiState.Loading    -> isSystemInDarkTheme()
    is SettingsUiState.Success -> when (uiState.settings.theme)
    {
        ThemeOptions.SYSTEM_DEFAULT -> isSystemInDarkTheme()
        ThemeOptions.LIGHT          -> false
        ThemeOptions.DARK           -> true
    }
}
