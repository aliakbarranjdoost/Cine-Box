package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCrew(
    val id: Int,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerialName("profile_path")
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