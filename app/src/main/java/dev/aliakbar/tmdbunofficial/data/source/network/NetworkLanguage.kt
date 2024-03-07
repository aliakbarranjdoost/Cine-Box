package dev.aliakbar.tmdbunofficial.data.source.network

import dev.aliakbar.tmdbunofficial.util.NETWORK_ENGLISH_NAME_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_ISO_6391_SERIAL_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkLanguage(
    @SerialName(NETWORK_ISO_6391_SERIAL_NAME)
    val iso: String,
    @SerialName(NETWORK_ENGLISH_NAME_SERIAL_NAME)
    val englishName: String,
    val name: String
)
