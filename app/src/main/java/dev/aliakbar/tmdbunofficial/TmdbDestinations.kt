package dev.aliakbar.tmdbunofficial

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tv
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface TmdbDestination
{
    val route: String
    val icon: ImageVector
}

object Home: TmdbDestination
{
    override val route = "home"
    override val icon = Icons.Filled.Home
}

object Bookmark: TmdbDestination
{
    override val route = "bookmark"
    override val icon = Icons.Filled.Bookmark
}

object Search: TmdbDestination
{
    override val route = "search"
    override val icon = Icons.Filled.Search
}

object Top: TmdbDestination
{
    override val route = "top"
    override val icon = Icons.AutoMirrored.Filled.List
}

object Setting: TmdbDestination
{
    override val route = "setting"
    override val icon = Icons.Filled.Settings
}

object Movie: TmdbDestination
{
    override val route = "movie"
    override val icon = Icons.Filled.Movie
    const val idArg = "id"
    val routeWithArgs = "${route}/{${idArg}}"
    val arguments = listOf(
        navArgument(idArg) { type = NavType.IntType}
    )
}

object Tv: TmdbDestination
{
    override val route = "tv"
    override val icon = Icons.Filled.Tv
    const val idArg = "id"
    val routeWithArgs = "${route}/{${idArg}}"
    val arguments = listOf(
        navArgument(idArg) { type = NavType.IntType}
    )
}

object Season: TmdbDestination
{
    override val route = "season"
    override val icon = Icons.Filled.Tv
    const val idArg = "id"
    const val seasonNumberArg = "season_number"
    val routeWithArgs = "${route}/{${idArg}}/{${seasonNumberArg}}"
    val arguments = listOf(
        navArgument(idArg) { type = NavType.IntType},
        navArgument(seasonNumberArg) { type = NavType.IntType}
    )
}

object Episode: TmdbDestination
{
    override val route = "episode"
    override val icon = Icons.Filled.Tv
    const val idArg = "id"
    const val seasonNumberArg = "season_number"
    const val episodeNumberArg = "episode_number"
    val routeWithArgs = "${route}/{${idArg}}/{${seasonNumberArg}}/{${episodeNumberArg}}"
    val arguments = listOf(
        navArgument(idArg) { type = NavType.IntType},
        navArgument(seasonNumberArg) { type = NavType.IntType},
        navArgument(episodeNumberArg) { type = NavType.IntType}
    )
}

val bottomNavigationItems = listOf(
    Home,
    Bookmark,
    Search,
    Top,
    Setting
)