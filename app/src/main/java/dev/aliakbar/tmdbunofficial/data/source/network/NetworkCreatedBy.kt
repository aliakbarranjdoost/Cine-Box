package dev.aliakbar.tmdbunofficial.data.source.network

import dev.aliakbar.tmdbunofficial.util.NETWORK_ALSO_KNOWN_AS_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_COMBINED_CREDITS_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_CREDIT_ID_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_IMDB_ID_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_KNOWN_FOR_DEPARTMENT_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_PLACE_OF_BIRTH_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_PROFILE_PATH_SERIAL_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCreatedBy(
    val id: Int,
    @SerialName(NETWORK_CREDIT_ID_SERIAL_NAME)
    val creditId: String,
    val name: String,
    val gender: Int,
    @SerialName(NETWORK_PROFILE_PATH_SERIAL_NAME)
    val profilePath: String? = null
)

@Serializable
data class NetworkPersonDetails(
    val id: Int,
    @SerialName(NETWORK_IMDB_ID_SERIAL_NAME)
    val imdbId : String? = null,
    val name: String,
    @SerialName(NETWORK_KNOWN_FOR_DEPARTMENT_SERIAL_NAME)
    val knownForDepartment: String,
    val adult: Boolean,
    @SerialName(NETWORK_ALSO_KNOWN_AS_SERIAL_NAME)
    val alsoKnownAs: List<String>,
    val biography: String,
    val birthday: String? = null,
    val deathDay: String? = null,
    val gender: Int,
    val homepage: String? = null,
    @SerialName(NETWORK_PLACE_OF_BIRTH_SERIAL_NAME)
    val placeOfBirth: String? = null,
    val popularity: Float,
    @SerialName(NETWORK_PROFILE_PATH_SERIAL_NAME)
    val profilePath: String,
    @SerialName(NETWORK_COMBINED_CREDITS_SERIAL_NAME)
    val combinedCredits: NetworkCombinedCreditsResponse,
    val images: NetworkProfileResponse
)