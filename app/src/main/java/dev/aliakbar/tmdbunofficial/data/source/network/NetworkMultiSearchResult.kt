package dev.aliakbar.tmdbunofficial.data.source.network

import dev.aliakbar.tmdbunofficial.util.NETWORK_BACKDROP_PATH_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_FIRST_AIR_DATE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_GENRE_IDS_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_KNOWN_FOR_DEPARTMENT_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_MEDIA_TYPE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_ORIGINAL_LANGUAGE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_ORIGINAL_NAME_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_ORIGINAL_TITLE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_ORIGIN_COUNTRY_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_POSTER_PATH_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_PROFILE_PATH_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_RELEASE_DATE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_VIDEO_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_VOTE_AVERAGE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_VOTE_COUNT_SERIAL_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkMultiSearchResult(
    val id: Int,
    val title: String? = null,
    @SerialName(NETWORK_ORIGINAL_TITLE_SERIAL_NAME)
    val originalTitle: String? = null,
    val overview: String? = null,
    @SerialName(NETWORK_ORIGINAL_LANGUAGE_SERIAL_NAME)
    val originalLanguage: String? = null,
    @SerialName(NETWORK_BACKDROP_PATH_SERIAL_NAME)
    val backdropPath: String? = null,
    @SerialName(NETWORK_POSTER_PATH_SERIAL_NAME)
    val posterPath: String? = null,
    @SerialName(NETWORK_MEDIA_TYPE_SERIAL_NAME)
    val mediaType: String,
    @SerialName(NETWORK_GENRE_IDS_SERIAL_NAME)
    val genreList: List<Int>? = null,
    @SerialName(NETWORK_FIRST_AIR_DATE_SERIAL_NAME)
    val firstAirDate: String? = null,
    val adult: Boolean,
    val popularity: Float,
    @SerialName(NETWORK_VOTE_AVERAGE_SERIAL_NAME)
    val voteAverage: Float? = null,
    @SerialName(NETWORK_VOTE_COUNT_SERIAL_NAME)
    val voteCount: Int? = null,

    val name: String? = null,
    @SerialName(NETWORK_ORIGINAL_NAME_SERIAL_NAME)
    val originalName: String? = null,
    val gender: Int? = null,
    @SerialName(NETWORK_KNOWN_FOR_DEPARTMENT_SERIAL_NAME)
    val knownForDepartment: String? = null,
    @SerialName(NETWORK_PROFILE_PATH_SERIAL_NAME)
    val profilePath: String? = null,
    @SerialName(NETWORK_RELEASE_DATE_SERIAL_NAME)
    val releaseDate: String? = null,
    @SerialName(NETWORK_VIDEO_SERIAL_NAME)
    val video: Boolean = false,

    @SerialName(NETWORK_ORIGIN_COUNTRY_SERIAL_NAME)
    val originCountryList: List<String>? = null
)
