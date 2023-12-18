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
