package dev.aliakbar.tmdbunofficial.data.source.network

import dev.aliakbar.tmdbunofficial.util.NETWORK_PROFILE_PATH_SERIAL_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCast(
    val id: Int,
    val name: String,
    @SerialName(NETWORK_PROFILE_PATH_SERIAL_NAME)
    val profilePath: String? = null,
    val character: String,
    /*val adult: Boolean,
    val gender: Int,
    @SerialName("known_for_department")
    val knownFor: String,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Float,
    @SerialName("cast_id")
    val castId: Int? = null,
    @SerialName("credit_id")
    val creditId: String,
    val order: Int*/
)