package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.LocalImageConfiguration
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkImageConfiguration

fun NetworkImageConfiguration.toExternal() = ImageConfiguration(
    baseUrl = baseUrl,
    secureBaseUrl = secureBaseUrl,
    backdropSizes = backdropSizes,
    logoSizes = logoSizes,
    posterSizes = posterSizes,
    profileSizes = profileSizes,
    stillSizes = stillSizes
)

fun NetworkImageConfiguration.toLocal(id: Int) = LocalImageConfiguration (
    id = id,
    baseUrl = baseUrl,
    secureBaseUrl = secureBaseUrl,
    backdropSizes = backdropSizes,
    logoSizes = logoSizes,
    posterSizes = posterSizes,
    profileSizes = profileSizes,
    stillSizes = stillSizes
)