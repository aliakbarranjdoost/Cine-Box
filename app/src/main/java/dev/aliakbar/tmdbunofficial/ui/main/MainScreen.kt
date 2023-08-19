package dev.aliakbar.tmdbunofficial.ui.main

import androidx.annotation.StringRes
import dev.aliakbar.tmdbunofficial.R

enum class TmdbScreen(@StringRes val title: Int)
{
    Splash(title = R.string.splash),
    Home(title = R.string.home),
    Bookmark(title = R.string.bookmark),
    Search(title = R.string.search),
    Top(title = R.string.top),
    Setting(title = R.string.setting),
    MovieDetails(title = R.string.movie_details),
    SeriesDetails(title = R.string.series_details)
}