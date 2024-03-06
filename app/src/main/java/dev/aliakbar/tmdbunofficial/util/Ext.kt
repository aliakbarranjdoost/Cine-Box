package dev.aliakbar.tmdbunofficial.util

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.ui.graphics.Color
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.MediaType
import dev.aliakbar.tmdbunofficial.data.Person
import dev.aliakbar.tmdbunofficial.data.PersonMovieAsCast
import dev.aliakbar.tmdbunofficial.data.PersonMovieAsCrew
import dev.aliakbar.tmdbunofficial.data.PersonTvAsCast
import dev.aliakbar.tmdbunofficial.data.PersonTvAsCrew
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkPersonMoviesAndTvs
import dev.aliakbar.tmdbunofficial.data.toExternalMovieCast
import dev.aliakbar.tmdbunofficial.data.toExternalMovieCrew
import dev.aliakbar.tmdbunofficial.data.toExternalTvCast
import dev.aliakbar.tmdbunofficial.data.toExternalTvCrew
import kotlin.math.roundToInt

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

fun calculateBackdropHeight(screenWidth : Int): Int
{
    return (screenWidth * 0.5625).roundToInt()
}

// TODO: This code badly need refactoring
fun mergeSimilarItems(persons: List<Person>): List<Person>
{
    val mergedPersons = persons.toMutableList()

    for ((indexi, person) in persons.withIndex())
    {
        val firstPerson = persons[indexi]
        val combinedRole = StringBuilder().append(firstPerson.role).append(", ")
        for ( index  in indexi + 1 until persons.size)
        {
            if (firstPerson.id == persons[index].id)
            {
                Log.d("Ext", "mergeSimilarItems: Yes ${persons[index]}")
                combinedRole.append(persons[index].role)
                combinedRole.append(", ")
                mergedPersons.remove(persons[index])
            }
            firstPerson.role = combinedRole.removeSuffix(", ").toString()
        }
    }
    return mergedPersons
}