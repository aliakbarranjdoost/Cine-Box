package dev.aliakbar.tmdbunofficial.data.source.network

import dev.aliakbar.tmdbunofficial.util.NETWORK_BACKDROP_PATH_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_CREATED_BY_STARS_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_FIRST_AIR_DATE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_IN_PRODUCTION_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_LAST_AIR_DATE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_NUMBER_OF_EPISODES_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_NUMBER_OF_SEASONS_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_ORIGINAL_LANGUAGE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_POSTER_PATH_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_VOTE_AVERAGE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_VOTE_COUNT_SERIAL_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTvDetails(
    val id: Int,
    val name: String,
    @SerialName(NETWORK_CREATED_BY_STARS_SERIAL_NAME)
    val createdBy: List<NetworkCreatedBy>,
    @SerialName(NETWORK_FIRST_AIR_DATE_SERIAL_NAME)
    val firstAirDate: String,
    @SerialName(NETWORK_LAST_AIR_DATE_SERIAL_NAME)
    val lastAirDate: String? = null,
    @SerialName(NETWORK_NUMBER_OF_EPISODES_SERIAL_NAME)
    val numberOfEpisodes: Int,
    @SerialName(NETWORK_NUMBER_OF_SEASONS_SERIAL_NAME)
    val numberOfSeasons: Int,
    val tagline: String,
    val overview: String,
    @SerialName(NETWORK_ORIGINAL_LANGUAGE_SERIAL_NAME)
    val originalLanguage: String,
    @SerialName(NETWORK_BACKDROP_PATH_SERIAL_NAME)
    val backdropPath: String,
    @SerialName(NETWORK_POSTER_PATH_SERIAL_NAME)
    val posterPath: String,
    val genres: List<NetworkGenre>,
    val adult: Boolean,
    val popularity: Float,
    @SerialName(NETWORK_VOTE_AVERAGE_SERIAL_NAME)
    val voteAverage: Float,
    @SerialName(NETWORK_VOTE_COUNT_SERIAL_NAME)
    val voteCount: Int,
    val seasons: List<NetworkSeason>,
    val homepage: String,
    @SerialName(NETWORK_IN_PRODUCTION_SERIAL_NAME)
    val isInProduction: Boolean,
    val status: String,
    val type: String,
    val credits: NetworkCredit,
    val videos: NetworkVideoResponse,
    val images: NetworkImageResponse,
    val recommendations: NetworkResponse<List<NetworkTrendingSeries>>
    /*@SerialName("original_name")
    val originalName: String,
    @SerialName("next_episode_to_air")
    val nextEpisodeToAir: NetworkEpisode?,
    @SerialName("last_episode_to_air")
    val lastEpisodeToAir: NetworkEpisode? = null,
    val networks: List<NetworkCompany>,
    @SerialName("origin_country")
    val originCountry: List<String>,
    @SerialName("production_countries")
    val productionCountries: List<NetworkCountry>,
    @SerialName("production_companies")
    val productionCompanies: List<NetworkCompany>,
    @SerialName("spoken_languages")
    val spokenLanguages: List<NetworkLanguage>,
    val languages: List<String>,*/
)
