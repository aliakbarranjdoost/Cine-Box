package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.network.NetworkCompany
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkEpisode
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkPerson

data class Tv(
    val id: Int,
    val name: String,
    val originalName: String,
    val createdBy: List<NetworkPerson>,
    //episode_run_ti
    val firstAirDate: String,
    val lastAirDate: String,
    val nextEpisodeToAir: NetworkEpisode?,
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
    val genres: List<Genre>,
    val adult: Boolean,
    val popularity: Float,
    val voteAverage: Float,
    val voteCount: Int,
    val productionCountries: List<Country>,
    val seasons: List<Season>,
    val homepage: String,
    val productionCompanies: List<Company>,
    val isInProduction: Boolean,
    val spokenLanguages: List<Language>,
    val status: String,
    val languages: List<String>,
    val type: String,
    val casts: List<Cast>,
    val crews: List<Crew>,
    val videos: List<Video>,
    val posters: List<Image>,
    val backdrops: List<Image>,
    val logos: List<Image>,
    val recommendations: List<Trend>
)