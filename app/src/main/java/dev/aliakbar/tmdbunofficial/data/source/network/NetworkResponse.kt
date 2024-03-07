package dev.aliakbar.tmdbunofficial.data.source.network

import dev.aliakbar.tmdbunofficial.util.NETWORK_GUEST_STARS_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_TOTAL_PAGES_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_TOTAL_RESULTS_SERIAL_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkResponse<T>(
    val page: Int,
    val results: T,
    @SerialName(NETWORK_TOTAL_PAGES_SERIAL_NAME)
    val totalPages: Int,
    @SerialName(NETWORK_TOTAL_RESULTS_SERIAL_NAME)
    val totalResults: Int
)

@Serializable
data class NetworkVideoResponse(
    val id: Int? = null,
    val results: List<NetworkVideo>
)

@Serializable
data class NetworkImageResponse(
    val backdrops: List<NetworkImage>,
    val logos: List<NetworkImage>,
    val posters: List<NetworkImage>
)

@Serializable
data class NetworkStillResponse(
    val stills: List<NetworkImage>
)

@Serializable
data class NetworkProfileResponse(
    val profiles: List<NetworkImage>
)

@Serializable
data class NetworkCreditAndGuestStarsResponse(
    val cast: List<NetworkCast>,
    val crew: List<NetworkCrew>,
    @SerialName(NETWORK_GUEST_STARS_SERIAL_NAME)
    val guestStars: List<NetworkCast>
)

@Serializable
data class NetworkCombinedCreditsResponse(
    val cast: List<NetworkPersonMoviesAndTvs>,
    val crew: List<NetworkPersonMoviesAndTvs>
)