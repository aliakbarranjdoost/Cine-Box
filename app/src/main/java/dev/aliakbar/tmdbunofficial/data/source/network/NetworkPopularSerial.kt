package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkPopularSerial(
    val id: Int,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    val overview: String,

    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("backdrop_path")
    val backdropPath: String = "",
    @SerialName("poster_path")
    val posterPath: String = "",
    @SerialName("genre_ids")
    val genreList: List<Int>,
    @SerialName("first_air_date")
    val firstAirDate: String? = null,
    val popularity: Float,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("vote_count")
    val voteCount: Int,
    @SerialName("origin_country")
    val originCountryList: List<String>
)
