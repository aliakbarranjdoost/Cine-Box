package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class NetworkPersonMoviesAndTvs(
    val id: Int,
    val title: String? = null,
    val backdropPath: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("media_type")
    val mediaType: String,
    @SerialName("first_air_date")
    val firstAirDate: String? = null,
    val voteAverage: Float? = null,
    @SerialName("vote_count")
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
    @SerialName("known_for_department")
    val knownForDepartment: String? = null,
    @SerialName("profile_path")
    val profilePath: String? = null,
    @SerialName("release_date")
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