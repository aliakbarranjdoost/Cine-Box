package dev.aliakbar.tmdbunofficial.data.source.network

import dev.aliakbar.tmdbunofficial.util.NETWORK_AIR_DATE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_EPISODE_NUMBER_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_EPISODE_TYPE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_SEASON_NUMBER_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_STILL_PATH_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_VOTE_AVERAGE_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_VOTE_COUNT_SERIAL_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkEpisodeDetails(
    val id: Int,
    val name: String,
    val overview: String,
    @SerialName(NETWORK_VOTE_AVERAGE_SERIAL_NAME)
    val voteAverage: Float,
    @SerialName(NETWORK_VOTE_COUNT_SERIAL_NAME)
    val voteCount: Int,
    @SerialName(NETWORK_AIR_DATE_SERIAL_NAME)
    val airDate: String,
    @SerialName(NETWORK_EPISODE_NUMBER_SERIAL_NAME)
    val episodeNumber: Int,
    @SerialName(NETWORK_EPISODE_TYPE_SERIAL_NAME)
    val episodeType: String? = null,
    val runtime: Int?,
    @SerialName(NETWORK_SEASON_NUMBER_SERIAL_NAME)
    val seasonNumber: Int,
    @SerialName(NETWORK_STILL_PATH_SERIAL_NAME)
    val stillPath: String?,
    val images : NetworkStillResponse,
    val credits : NetworkCreditAndGuestStarsResponse,
    val videos: NetworkVideoResponse
    /*@SerialName("production_code")
    val productionCode: String,
    @SerialName("guest_stars")
    val guestStars: List<NetworkCast>*/
)
