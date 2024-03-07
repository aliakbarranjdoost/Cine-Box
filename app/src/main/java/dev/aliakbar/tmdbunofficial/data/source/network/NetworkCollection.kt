package dev.aliakbar.tmdbunofficial.data.source.network

import dev.aliakbar.tmdbunofficial.util.NETWORK_BACKDROP_PATH_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_POSTER_PATH_SERIAL_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCollection(
    val id: Int,
    val name: String,
    @SerialName(NETWORK_POSTER_PATH_SERIAL_NAME)
    val posterPath: String = "",
    @SerialName(NETWORK_BACKDROP_PATH_SERIAL_NAME)
    val backdropPath: String
)
