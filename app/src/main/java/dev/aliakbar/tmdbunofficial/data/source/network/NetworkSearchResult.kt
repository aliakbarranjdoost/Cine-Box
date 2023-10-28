package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkSearchResult(
    val id: Int,
    val title: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("media_type")
    val mediaType: String,
    @SerialName("genre_ids")
    val genreList: List<Int>,
    @SerialName("first_air_date")
    val releaseDate: String,
    val adult: Boolean,
    val popularity: Float,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("vote_count")
    val voteCount: Int,

    val name: String,
    @SerialName("original_name")
    val originalName: String,
    val gender: Int,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    @SerialName("profile_path")
    val profilePath: String? = null,
    @SerialName("known_for")
    val knownFor: String? = null,

    @SerialName("origin_country")
    val originCountryList: List<String>
)
