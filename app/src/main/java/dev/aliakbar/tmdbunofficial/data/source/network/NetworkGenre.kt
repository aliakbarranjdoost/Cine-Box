package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.Serializable

@Serializable
data class NetworkGenre(
    val id: Int,
    val name: String
)
