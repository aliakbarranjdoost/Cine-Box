package dev.aliakbar.tmdbunofficial.util

import android.content.Context
import android.content.Intent
import androidx.compose.ui.graphics.Color
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.MediaType
import dev.aliakbar.tmdbunofficial.data.PersonMovieAsCast
import dev.aliakbar.tmdbunofficial.data.PersonMovieAsCrew
import dev.aliakbar.tmdbunofficial.data.PersonTvAsCast
import dev.aliakbar.tmdbunofficial.data.PersonTvAsCrew
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkPersonMoviesAndTvs
import dev.aliakbar.tmdbunofficial.data.toExternalMovieCast
import dev.aliakbar.tmdbunofficial.data.toExternalMovieCrew
import dev.aliakbar.tmdbunofficial.data.toExternalTvCast
import dev.aliakbar.tmdbunofficial.data.toExternalTvCrew

fun Float.toDegree(): Float
{
    return this * 12
}

fun Float.convertDegreeToHsvColor(): Color
{
    return Color.hsv(toDegree(),1F,1F)
}

fun List<NetworkPersonMoviesAndTvs>.separateMoviesAndTvsCast(
    baseBackdropUrl: String,
    basePosterUrl: String,
) : Pair<List<PersonMovieAsCast>,List<PersonTvAsCast>>
{
    val movies = mutableListOf<PersonMovieAsCast>()
    val tvs = mutableListOf<PersonTvAsCast>()

    this.forEach()
    {
        if (it.mediaType == "movie")
        {
            movies.add(it.toExternalMovieCast(baseBackdropUrl, basePosterUrl))
        }
        else
        {
            tvs.add(it.toExternalTvCast(baseBackdropUrl, basePosterUrl))
        }
    }

    return Pair<List<PersonMovieAsCast>,List<PersonTvAsCast>>(movies,tvs)
}

fun List<NetworkPersonMoviesAndTvs>.separateMoviesAndTvsCrew(
    baseBackdropUrl: String,
    basePosterUrl: String,
) : Pair<List<PersonMovieAsCrew>,List<PersonTvAsCrew>>
{
    val movies = mutableListOf<PersonMovieAsCrew>()
    val tvs = mutableListOf<PersonTvAsCrew>()

    this.forEach()
    {
        if (it.mediaType == "movie")
        {
            movies.add(it.toExternalMovieCrew(baseBackdropUrl, basePosterUrl))
        }
        else
        {
            tvs.add(it.toExternalTvCrew(baseBackdropUrl, basePosterUrl))
        }
    }

    return Pair<List<PersonMovieAsCrew>,List<PersonTvAsCrew>>(movies,tvs)
}

fun Context.share(mediaType: MediaType, id: Int)
{
    val shareMessage = getString(R.string.share_message, mediaType.name.lowercase(), id)
    val sendIntent: Intent = Intent().apply()
    {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareMessage)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Share")
    startActivity(shareIntent)
}