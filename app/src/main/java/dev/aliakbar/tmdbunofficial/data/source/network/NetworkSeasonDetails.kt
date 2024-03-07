package dev.aliakbar.tmdbunofficial.data.source.network

import dev.aliakbar.tmdbunofficial.util.NETWORK_AIR_DATE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_POSTER_PATH_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_SEASON_NUMBER_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_VOTE_AVERAGE_SERIAL_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkSeasonDetails(
    val id: Int,
    val name: String,
    val overview: String,
    val episodes: List<NetworkEpisode>,
    @SerialName(NETWORK_AIR_DATE_SERIAL_NAME)
    val airDate: String?,
    @SerialName(NETWORK_SEASON_NUMBER_SERIAL_NAME)
    val seasonNumber: Int,
    @SerialName(NETWORK_VOTE_AVERAGE_SERIAL_NAME)
    val voteAverage: Float,
    @SerialName(NETWORK_POSTER_PATH_SERIAL_NAME)
    val posterPath: String?
)