package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkSeason(
    val id: Int,
    val name: String,
    val overview: String,
    @SerialName("episode_count")
    val episodeCount: Int,
    @SerialName("air_date")
    val airDate: String,
    @SerialName("season_number")
    val seasonNumber: Int,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("poster_path")
    val posterPath: String
)