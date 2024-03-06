package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO: Set a suitable default value for optional types
@Serializable
data class NetworkTrendingMovie(
    val id: Int,
    val title: String,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("media_type")
    val mediaType: String? = null,
    val popularity: Float,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("vote_count")
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
