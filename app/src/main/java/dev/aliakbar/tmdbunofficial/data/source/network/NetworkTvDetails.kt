package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTvDetails(
    val id: Int,
    val name: String,
    @SerialName("created_by")
    val createdBy: List<NetworkCreatedBy>,
    @SerialName("first_air_date")
    val firstAirDate: String,
    @SerialName("last_air_date")
    val lastAirDate: String? = null,
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int,
    val tagline: String,
    val overview: String,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("backdrop_path")
    val backdropPath: String,
    @SerialName("poster_path")
    val posterPath: String,
    val genres: List<NetworkGenre>,
    val adult: Boolean,
    val popularity: Float,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("vote_count")
    val voteCount: Int,
    val seasons: List<NetworkSeason>,
    val homepage: String,
    @SerialName("in_production")
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
