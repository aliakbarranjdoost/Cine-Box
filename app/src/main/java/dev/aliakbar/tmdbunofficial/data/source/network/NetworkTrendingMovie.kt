package dev.aliakbar.tmdbunofficial.data.source.network

import dev.aliakbar.tmdbunofficial.util.NETWORK_BACKDROP_PATH_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_MEDIA_TYPE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_POSTER_PATH_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_VOTE_AVERAGE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_VOTE_COUNT_SERIAL_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO: Set a suitable default value for optional types
@Serializable
data class NetworkTrendingMovie(
    val id: Int,
    val title: String,
    @SerialName(NETWORK_BACKDROP_PATH_SERIAL_NAME)
    val backdropPath: String? = null,
    @SerialName(NETWORK_POSTER_PATH_SERIAL_NAME)
    val posterPath: String? = null,
    @SerialName(NETWORK_MEDIA_TYPE_SERIAL_NAME)
    val mediaType: String? = null,
    val popularity: Float,
    @SerialName(NETWORK_VOTE_AVERAGE_SERIAL_NAME)
    val voteAverage: Float,
    @SerialName(NETWORK_VOTE_COUNT_SERIAL_NAME)
    val voteCount: Int
    /*@SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("genre_ids")
    val genreList: List<Int>,
    @SerialName("release_date")
    val releaseDate: String,
    val adult: Boolean,
    val video: Boolean*/
)
