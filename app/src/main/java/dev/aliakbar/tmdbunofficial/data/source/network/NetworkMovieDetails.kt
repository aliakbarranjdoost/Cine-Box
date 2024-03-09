package dev.aliakbar.tmdbunofficial.data.source.network

import dev.aliakbar.tmdbunofficial.util.NETWORK_BACKDROP_PATH_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_BELONGS_TO_COLLECTION_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_IMDB_ID_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_ORIGINAL_LANGUAGE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_ORIGINAL_TITLE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_POSTER_PATH_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_PRODUCTION_COMPANIES_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_PRODUCTION_COUNTRIES_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_RELEASE_DATE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_SPOKEN_LANGUAGES_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_VOTE_AVERAGE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_VOTE_COUNT_SERIAL_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkMovieDetails(
    val id: Int,
    @SerialName(NETWORK_IMDB_ID_SERIAL_NAME)
    val imdbId: String,
    val title: String,
    @SerialName(NETWORK_ORIGINAL_TITLE_SERIAL_NAME)
    val originalTitle: String,
    val tagline: String,
    val overview: String,
    @SerialName(NETWORK_ORIGINAL_LANGUAGE_SERIAL_NAME)
    val originalLanguage: String,
    @SerialName(NETWORK_BACKDROP_PATH_SERIAL_NAME)
    val backdropPath: String? = null,
    @SerialName(NETWORK_POSTER_PATH_SERIAL_NAME)
    val posterPath: String? = null,
    val genres: List<NetworkGenre>,
    @SerialName(NETWORK_RELEASE_DATE_SERIAL_NAME)
    val releaseDate: String,
    val adult: Boolean,
    val video: Boolean,
    val popularity: Float,
    @SerialName(NETWORK_VOTE_AVERAGE_SERIAL_NAME)
    val voteAverage: Float,
    @SerialName(NETWORK_VOTE_COUNT_SERIAL_NAME)
    val voteCount: Int,
    @SerialName(NETWORK_BELONGS_TO_COLLECTION_SERIAL_NAME)
    val collection: NetworkCollection? = null,
    @SerialName(NETWORK_PRODUCTION_COUNTRIES_SERIAL_NAME)
    val productionCountries: List<NetworkCountry>,
    val budget: Int,
    val homepage: String,
    @SerialName(NETWORK_PRODUCTION_COMPANIES_SERIAL_NAME)
    val productionCompanies: List<NetworkCompany>,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    @SerialName(NETWORK_SPOKEN_LANGUAGES_SERIAL_NAME)
    val spokenLanguages: List<NetworkLanguage>,
    val credits: NetworkCredit,
    val videos: NetworkVideoResponse,
    val images: NetworkImageResponse,
    val recommendations: NetworkResponse<List<NetworkTrendingMovie>>
)
