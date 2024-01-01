package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkEpisodeDetails(
    val id: Int,
    val name: String,
    val overview: String,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("vote_count")
    val voteCount: Int,
    @SerialName("air_date")
    val airDate: String,
    @SerialName("episode_number")
    val episodeNumber: Int,
    @SerialName("episode_type")
    val episodeType: String? = null,
    @SerialName("production_code")
    val productionCode: String,
    val runtime: Int?,
    @SerialName("season_number")
    val seasonNumber: Int,
    @SerialName("still_path")
    val stillPath: String?,
    val images : NetworkStillResponse,
    val credits : NetworkCreditAndGuestStarsResponse,
    val videos: NetworkVideoResponse,
    //@SerialName("guest_stars")
    //val guestStars: List<NetworkCast>
)
