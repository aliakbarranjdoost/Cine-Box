package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.Serializable

@Serializable
data class NetworkCredit(
    val cast: List<NetworkCast>,
    val crew: List<NetworkCrew>
)
