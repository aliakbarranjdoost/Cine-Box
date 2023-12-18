package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.network.NetworkCompany
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkCountry
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkCredit
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkEpisode
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkGenre
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkImageResponse
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkLanguage
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkPerson
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkResponse
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkSeason
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkTrendingMovie
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkVideoResponse

data class TvDetails(
    val id: Int,
    val name: String,
    val originalName: String,
    val createdBy: List<NetworkPerson>,
    //episode_run_ti
    val firstAirDate: String,
    val lastAirDate: String,
    val nextEpisodeToAir: String?,
    val lastEpisodeToAir: NetworkEpisode,
    val networks: List<NetworkCompany>,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
    val originCountry: List<String>,
    val tagline: String,
    val overview: String,
    val originalLanguage: String,
    val backdropPath: String,
    val posterPath: String,
    val genres: List<NetworkGenre>,
    val adult: Boolean,
    val popularity: Float,
    val voteAverage: Float,
    val voteCount: Int,
    val productionCountries: List<Country>,
    val seasons: List<Season>,
    val homepage: String,
    val productionCompanies: List<Company>,
    val isInProduction: Boolean,
    val spokenLLanguages: List<Language>,
    val status: String,
    val languages: List<String>,
    val type: String,
    val credits: NetworkCredit,
    val videos: NetworkVideoResponse,
    val images: NetworkImageResponse,
    val recommendations: NetworkResponse<List<NetworkTrendingMovie>>
)