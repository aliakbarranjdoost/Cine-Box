package dev.aliakbar.tmdbunofficial.data.source.network

import dev.aliakbar.tmdbunofficial.util.NETWORK_FIRST_AIR_DATE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_KNOWN_FOR_DEPARTMENT_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_MEDIA_TYPE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_POSTER_PATH_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_PROFILE_PATH_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_RELEASE_DATE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_VOTE_COUNT_SERIAL_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class NetworkPersonMoviesAndTvs(
    val id: Int,
    val title: String? = null,
    val backdropPath: String? = null,
    @SerialName(NETWORK_POSTER_PATH_SERIAL_NAME)
    val posterPath: String? = null,
    @SerialName(NETWORK_MEDIA_TYPE_SERIAL_NAME)
    val mediaType: String,
    @SerialName(NETWORK_FIRST_AIR_DATE_SERIAL_NAME)
    val firstAirDate: String? = null,
    val voteAverage: Float? = null,
    @SerialName(NETWORK_VOTE_COUNT_SERIAL_NAME)
    val voteCount: Int? = null,
    /*@SerialName("original_title")
    val originalTitle: String? = null,*/
    /*val overview: String? = null,
    @SerialName("original_language")*/
    /*val originalLanguage: String? = null,
    @SerialName("backdrop_path")*/
    /*@SerialName("genre_ids")
    val genreList: List<Int>? = null,*/
//    val adult: Boolean,
    /*val popularity: Float,
    @SerialName("vote_average")*/

    val name: String? = null,
    val gender: Int? = null,
    @SerialName(NETWORK_KNOWN_FOR_DEPARTMENT_SERIAL_NAME)
    val knownForDepartment: String? = null,
    @SerialName(NETWORK_PROFILE_PATH_SERIAL_NAME)
    val profilePath: String? = null,
    @SerialName(NETWORK_RELEASE_DATE_SERIAL_NAME)
    val releaseDate: String? = null,
    /*@SerialName("original_name")
    val originalName: String? = null,
    @SerialName("known_for")
    val knownFor: List<String?> = emptyList(),
    @SerialName("video")
    val video: Boolean = false,
    @SerialName("credit_id")
    val creditId: String,*/
    val character: String? = null,

    val department: String? = null,
    val job: String? = null
)