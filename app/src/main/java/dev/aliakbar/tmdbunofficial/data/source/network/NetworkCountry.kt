package dev.aliakbar.tmdbunofficial.data.source.network

import dev.aliakbar.tmdbunofficial.util.NETWORK_ISO_31661_SERIAL_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCountry(
    @SerialName(NETWORK_ISO_31661_SERIAL_NAME)
    val iso: String,
    val name: String
)
