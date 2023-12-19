package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTvDetails(
    val id: Int,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("created_by")
    val createdBy: List<NetworkPerson>,
    //episode_run_ti
    @SerialName("first_air_date")
    val firstAirDate: String,
    @SerialName("last_air_date")
    val lastAirDate: String,
    @SerialName("next_episode_to_air")
    val nextEpisodeToAir: NetworkEpisode,
    @SerialName("last_episode_to_air")
    val lastEpisodeToAir: NetworkEpisode,
    val networks: List<NetworkCompany>,
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerialName("origin_country")
    val originCountry: List<String>,
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
    @SerialName("production_countries")
    val productionCountries: List<NetworkCountry>,
    val seasons: List<NetworkSeason>,
    val homepage: String,
    @SerialName("production_companies")
    val productionCompanies: List<NetworkCompany>,
    @SerialName("in_production")
    val isInProduction: Boolean,
    @SerialName("spoken_languages")
    val spokenLanguages: List<NetworkLanguage>,
    val status: String,
    val languages: List<String>,
    val type: String,
    val credits: NetworkCredit,
    val videos: NetworkVideoResponse,
    val images: NetworkImageResponse,
    val recommendations: NetworkResponse<List<NetworkTrendingSeries>>
)
