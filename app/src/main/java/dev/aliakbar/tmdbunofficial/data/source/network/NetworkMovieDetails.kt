package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkMovieDetails(
    val id: Int,
    @SerialName("imdb_id")
    val imdbId: String,
    val title: String,
    @SerialName("original_title")
    val originalTitle: String,
    val tagline: String,
    val overview: String,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("backdrop_path")
    val backdropPath: String,
    @SerialName("poster_path")
    val posterPath: String,
    val genres: List<NetworkGenre>,
    @SerialName("release_date")
    val releaseDate: String,
    val adult: Boolean,
    val video: Boolean,
    val popularity: Float,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("vote_count")
    val voteCount: Int,
    @SerialName("belongs_to_collection")
    val collection: NetworkCollection,
    @SerialName("production_countries")
    val productionCountries: List<NetworkCountry>,
    val budget: Int,
    val homepage: String,
    @SerialName("production_companies")
    val productionCompanies: List<NetworkCompany>,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    @SerialName("spoken_languages")
    val spokenLanguages: List<NetworkLanguage>
)
