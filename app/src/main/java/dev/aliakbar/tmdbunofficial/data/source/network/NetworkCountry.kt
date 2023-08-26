package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCountry(
    @SerialName("iso_3166_1")
    val iso: String,
    val name: String
)
