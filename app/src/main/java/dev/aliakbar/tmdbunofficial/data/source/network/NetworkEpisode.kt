package dev.aliakbar.tmdbunofficial.data.source.network

import dev.aliakbar.tmdbunofficial.util.NETWORK_EPISODE_NUMBER_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_EPISODE_TYPE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_SEASON_NUMBER_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_SHOW_ID_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_STILL_PATH_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_VOTE_AVERAGE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_VOTE_COUNT_SERIAL_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkEpisode(
    val id: Int,
    val name: String,
    val overview: String,
    @SerialName(NETWORK_VOTE_AVERAGE_SERIAL_NAME)
    val voteAverage: Float,
    @SerialName(NETWORK_VOTE_COUNT_SERIAL_NAME)
    val voteCount: Int,
    @SerialName(NETWORK_EPISODE_NUMBER_SERIAL_NAME)
    val episodeNumber: Int,
    @SerialName(NETWORK_EPISODE_TYPE_SERIAL_NAME)
    val episodeType: String,
    @SerialName(NETWORK_SEASON_NUMBER_SERIAL_NAME)
    val seasonNumber: Int,
    @SerialName(NETWORK_SHOW_ID_SERIAL_NAME)
    val showId: Int,
    @SerialName(NETWORK_STILL_PATH_SERIAL_NAME)
    val stillPath: String?
    /*@SerialName("air_date")
    val airDate: String,
    @SerialName("production_code")
    val productionCode: String,
    val runtime: Int?*/
)
