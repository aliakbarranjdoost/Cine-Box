package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkPerson(
    val id: Int,
    @SerialName("credit_id")
    val creditId: String,
    val name: String,
    val gender: Int,
    @SerialName("profile_path")
    val profilePath: String
)

@Serializable
data class NetworkPersonDetails(
    val adult: Boolean,
    @SerialName("also_known_as")
    val alsoKnownAs: List<String>,
    val biography: String,
    val birthday: String,
    val deathDay: String? = null,
    val gender: Int,
    val homepage: String? = null,
    val id: Int,
    @SerialName("imdb_id")
    val imdbId : String,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerialName("place_of_birth")
    val placeOfBirth: String,
    val popularity: Float,
    @SerialName("profile_path")
    val profilePath: String,
    @SerialName("combined_credits")
    val combinedCredits: NetworkCombinedCreditsResponse,
    val images: NetworkProfileResponse
)