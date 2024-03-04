package dev.aliakbar.tmdbunofficial

import androidx.navigation.NavType
import androidx.navigation.navArgument

const val ID_ARG = "id"
interface TmdbDestination
{
    val route: String
}

interface TmdbMainDestination: TmdbDestination
{
    override val route: String
    val defaultIcon: Int
    val selectedIcon: Int
}

interface TmdbSecondaryDestination: TmdbDestination
{
    override val route: String
}

object Home: TmdbMainDestination
{
    override val route = "home"
    override val defaultIcon = R.drawable.ic_outline_home
    override val selectedIcon: Int = R.drawable.ic_baseline_home
}

object Bookmark: TmdbMainDestination
{
    override val route = "bookmark"
    override val defaultIcon = R.drawable.ic_outline_bookmarks
    override val selectedIcon: Int = R.drawable.ic_baseline_bookmarks
}

object Search: TmdbMainDestination
{
    override val route = "search"
    override val defaultIcon = R.drawable.ic_search
    override val selectedIcon = R.drawable.ic_search
}

object Top: TmdbMainDestination
{
    override val route = "top"
    override val defaultIcon = R.drawable.ic_list
    override val selectedIcon = R.drawable.ic_list
}

object Setting: TmdbMainDestination
{
    override val route = "setting"
    override val defaultIcon = R.drawable.ic_outline_settings
    override val selectedIcon = R.drawable.ic_baseline_settings
}

object Movie: TmdbSecondaryDestination
{
    override val route = "movie"
    private const val idArg = ID_ARG
    val routeWithArgs = "${route}/{${idArg}}"
    val arguments = listOf(
        navArgument(idArg) { type = NavType.IntType}
    )
}

object Tv: TmdbSecondaryDestination
{
    override val route = "tv"
    private const val idArg = ID_ARG
    val routeWithArgs = "${route}/{${idArg}}"
    val arguments = listOf(
        navArgument(idArg) { type = NavType.IntType}
    )
}

object Season: TmdbSecondaryDestination
{
    override val route = "season"
    private const val idArg = ID_ARG
    const val seasonNumberArg = "season_number"
    val routeWithArgs = "${route}/{${idArg}}/{${seasonNumberArg}}"
    val arguments = listOf(
        navArgument(idArg) { type = NavType.IntType},
        navArgument(seasonNumberArg) { type = NavType.IntType}
    )
}

object Episode: TmdbSecondaryDestination
{
    override val route = "episode"
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

object GenreTop: TmdbSecondaryDestination
{
    override val route = "genreTop"
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

object Person: TmdbSecondaryDestination
{
    override val route = "person"
    private const val idArg = ID_ARG
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