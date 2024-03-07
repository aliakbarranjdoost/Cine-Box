package dev.aliakbar.tmdbunofficial.data.source.network

import dev.aliakbar.tmdbunofficial.util.NETWORK_CHANGE_KEYS_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_IMAGES_SERIAL_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkConfigurationResponse(
    @SerialName(NETWORK_IMAGES_SERIAL_NAME)
    val imageConfiguration: NetworkImageConfiguration,
    @SerialName(NETWORK_CHANGE_KEYS_SERIAL_NAME)
    val changeKeys: List<String>
)
