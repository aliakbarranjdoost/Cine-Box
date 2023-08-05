package dev.aliakbar.tmdbunofficial.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkConfiguration(
    @SerialName("images")
    val imageConfiguration: NetworkImageConfiguration,
    @SerialName("change_keys")
    val changeKeys: List<String>
)
