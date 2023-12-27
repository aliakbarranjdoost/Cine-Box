package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.network.NetworkEpisode

data class SeasonDetails(
    val id: Int,
    val _id: String,
    val name: String,
    val overview: String,
    val episodes: List<NetworkEpisode>,
    val airDate: String?,
    val seasonNumber: Int,
    val voteAverage: Float,
    val posterPath: String?
)
