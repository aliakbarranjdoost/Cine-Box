package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTrendingSeries(
    val id: Int,
    val name: String,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("media_type")
    val mediaType: String? = null,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("vote_count")
    val voteCount: Int
    /*@SerialName("original_name")
    val originalName: String,
    val overview: String,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("genre_ids")
    val genreList: List<Int>,
    @SerialName("first_air_date")
    val firstAirDate: String,
    val adult: Boolean? = true,
    val popularity: Float,
    @SerialName("origin_country")
    val originCountryList: List<String>*/
)
