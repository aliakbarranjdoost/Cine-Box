package dev.aliakbar.tmdbunofficial.data.source.network

import dev.aliakbar.tmdbunofficial.util.NETWORK_KNOWN_FOR_DEPARTMENT_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_PROFILE_PATH_SERIAL_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCrew(
    val id: Int,
    @SerialName(NETWORK_KNOWN_FOR_DEPARTMENT_SERIAL_NAME)
    val knownForDepartment: String,
    val name: String,
    @SerialName(NETWORK_PROFILE_PATH_SERIAL_NAME)
    val profilePath: String? = null,
    val department: String,
    val job: String
    /*val adult: Boolean,
    val gender: Int,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Float,
    @SerialName("credit_id")
    val creditId: String*/
)