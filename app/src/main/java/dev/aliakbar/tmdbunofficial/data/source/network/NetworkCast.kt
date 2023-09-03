package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCast(
    val id: Int,
    val adult: Boolean,
    val gender: Int,
    @SerialName("known_for_department")
    val knownFor: String,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Float,
    @SerialName("profile_path")
    val profilePath: String? = null,
    @SerialName("cast_id")
    val castId: Int,
    val character: String,
    @SerialName("credit_id")
    val creditId: String,
    val order: Int
)

@Serializable
data class NetworkCrew(
    val id: Int,
    val adult: Boolean,
    val gender: Int,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Float,
    @SerialName("profile_path")
    val profilePath: String? = null,
    @SerialName("credit_id")
    val creditId: String,
    val department: String,
    val job: String
)