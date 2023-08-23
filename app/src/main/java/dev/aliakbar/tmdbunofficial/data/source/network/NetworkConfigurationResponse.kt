package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkConfigurationResponse(
    @SerialName("images")
    val imageConfiguration: NetworkImageConfiguration,
    @SerialName("change_keys")
    val changeKeys: List<String>
)
