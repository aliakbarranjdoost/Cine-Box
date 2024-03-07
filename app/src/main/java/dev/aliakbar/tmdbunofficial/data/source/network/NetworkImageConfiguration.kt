package dev.aliakbar.tmdbunofficial.data.source.network

import dev.aliakbar.tmdbunofficial.util.NETWORK_BACKDROP_SIZES_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_BASE_URL_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_LOGO_SIZES_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_POSTER_SIZES_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_PROFILE_SIZES_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_SECURE_BASE_URL_SERIAL_NAME
import dev.aliakbar.tmdbunofficial.util.NETWORK_STILL_SIZES_SERIAL_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkImageConfiguration(
    @SerialName(NETWORK_BASE_URL_SERIAL_NAME)
    val baseUrl: String,
    @SerialName(NETWORK_SECURE_BASE_URL_SERIAL_NAME)
    val secureBaseUrl: String,
    @SerialName(NETWORK_BACKDROP_SIZES_SERIAL_NAME)
    val backdropSizes: List<String>,
    @SerialName(NETWORK_LOGO_SIZES_SERIAL_NAME)
    val logoSizes: List<String>,
    @SerialName(NETWORK_POSTER_SIZES_SERIAL_NAME)
    val posterSizes: List<String>,
    @SerialName(NETWORK_PROFILE_SIZES_SERIAL_NAME)
    val profileSizes: List<String>,
    @SerialName(NETWORK_STILL_SIZES_SERIAL_NAME)
    val stillSizes: List<String>,
)