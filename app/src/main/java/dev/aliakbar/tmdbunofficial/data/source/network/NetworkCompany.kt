package dev.aliakbar.tmdbunofficial.data.source.network

import dev.aliakbar.tmdbunofficial.util.NETWORK_LOGO_PATH_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_ORIGIN_COUNTRY_SERIAL_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCompany(
    val id: Int,
    val name: String,
    @SerialName(NETWORK_LOGO_PATH_SERIAL_NAME)
    val logoPath: String? = null,
    @SerialName(NETWORK_ORIGIN_COUNTRY_SERIAL_NAME)
    val originCountry: String
)
