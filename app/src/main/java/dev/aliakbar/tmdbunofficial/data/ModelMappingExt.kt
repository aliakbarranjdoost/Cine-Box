package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.LocalImageConfiguration
import dev.aliakbar.tmdbunofficial.data.source.local.LocalTrend
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkImageConfiguration
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkTrendingMovie
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkTrendingSeries

fun NetworkImageConfiguration.toExternal() = ImageConfiguration(
    baseUrl = baseUrl,
    secureBaseUrl = secureBaseUrl,
    backdropSizes = backdropSizes,
    logoSizes = logoSizes,
    posterSizes = posterSizes,
    profileSizes = profileSizes,
    stillSizes = stillSizes
)

fun NetworkImageConfiguration.toLocal(id: Int) = LocalImageConfiguration(
    id = id,
    baseUrl = baseUrl,
    secureBaseUrl = secureBaseUrl,
    backdropSizes = backdropSizes,
    logoSizes = logoSizes,
    posterSizes = posterSizes,
    profileSizes = profileSizes,
    stillSizes = stillSizes
)

fun NetworkTrendingMovie.toExternal(basePosterUrl: String) = Trend(
    id = id,
    title = title,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    rank = 0
)

@JvmName("networkToExternal")
fun List<NetworkTrendingMovie>.toExternal(basePosterUrl: String) =
    map { it.toExternal(basePosterUrl) }

fun NetworkTrendingMovie.toLocal(basePosterUrl: String, rank: Int) = LocalTrend(
    id = id,
    title = title,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    rank = rank
)

@JvmName("networkToLocal")
fun List<NetworkTrendingMovie>.toLocal(basePosterUrl: String) = mapIndexed()
{ index, networkTrendMovie ->
    networkTrendMovie.toLocal(basePosterUrl, index.inc())
}

fun LocalTrend.toExternal() = Trend(
    id = id,
    title = title,
    score = score,
    poster = poster,
    rank = rank
)

@JvmName("localToExternal")
fun List<LocalTrend>.toExternal() = map()
{
    localTrend ->
    localTrend.toExternal()
}

fun NetworkTrendingSeries.toExternal(basePosterUrl: String) = Trend(
    id = id,
    title = name,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    rank = 0
)

//@JvmName("networkToExternal")
fun List<NetworkTrendingSeries>.toExternal(basePosterUrl: String) =
    map { it.toExternal(basePosterUrl) }

fun NetworkTrendingSeries.toLocal(basePosterUrl: String, rank: Int) = LocalTrend(
    id = id,
    title = name,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    rank = rank
)

//@JvmName("networkToLocal")
fun List<NetworkTrendingSeries>.toLocal(basePosterUrl: String) = mapIndexed()
{ index, networkTrendMovie ->
    networkTrendMovie.toLocal(basePosterUrl, index.inc())
}