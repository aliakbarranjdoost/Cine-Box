package dev.aliakbar.tmdbunofficial

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

const val ID_ARG = "id"
interface TmdbDestination
{
    val route: String
    val icon: ImageVector
}

object Home: TmdbDestination
{
    override val route = "home"
    override val icon = Icons.Outlined.Home
}

object Bookmark: TmdbDestination
{
    override val route = "bookmark"
    override val icon = Icons.Outlined.Bookmarks
}

object Search: TmdbDestination
{
    override val route = "search"
    override val icon = Icons.Outlined.Search
}

object Top: TmdbDestination
{
    override val route = "top"
    override val icon = Icons.AutoMirrored.Outlined.List
}

object Setting: TmdbDestination
{
    override val route = "setting"
    override val icon = Icons.Outlined.Settings
}

object Movie: TmdbDestination
{
    override val route = "movie"
    override val icon = Icons.Default.Movie
    private const val idArg = ID_ARG
    val routeWithArgs = "${route}/{${idArg}}"
    val arguments = listOf(
        navArgument(idArg) { type = NavType.IntType}
    )
}

object Tv: TmdbDestination
{
    override val route = "tv"
    override val icon = Icons.Default.Tv
    private const val idArg = ID_ARG
    val routeWithArgs = "${route}/{${idArg}}"
    val arguments = listOf(
        navArgument(idArg) { type = NavType.IntType}
    )
}

object Season: TmdbDestination
{
    override val route = "season"
    override val icon = Icons.Default.Tv
    private const val idArg = ID_ARG
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
    override val icon = Icons.Default.Tv
    private const val idArg = ID_ARG
    const val seasonNumberArg = "season_number"
    const val episodeNumberArg = "episode_number"
    val routeWithArgs = "${route}/{${idArg}}/{${seasonNumberArg}}/{${episodeNumberArg}}"
    val arguments = listOf(
        navArgument(idArg) { type = NavType.IntType},
        navArgument(seasonNumberArg) { type = NavType.IntType},
        navArgument(episodeNumberArg) { type = NavType.IntType}
    )
}

object GenreTop: TmdbDestination
{
    override val route = "genreTop"
    override val icon = Icons.Default.Category
    const val genreIdArg = "genreId"
    const val genreNameArg = "genreName"
    const val typeArg = "type"
    val routeWithArgs = "$route/{$genreIdArg}/{$genreNameArg}/{$typeArg}"
    val arguments = listOf(
        navArgument(genreIdArg) { type = NavType.IntType},
        navArgument(genreNameArg) { type = NavType.StringType},
        navArgument(typeArg) { type = NavType.BoolType},
    )
}

object Person: TmdbDestination
{
    override val route = "person"
    override val icon = Icons.Default.Person
    const val idArg = ID_ARG
    val routeWithArg = "$route/{$idArg}"
    val arguments = listOf(
        navArgument(idArg) { type = NavType.IntType }
    )
}

val bottomNavigationItems = listOf(
    Home,
    Bookmark,
    Search,
    Top,
    Setting
)