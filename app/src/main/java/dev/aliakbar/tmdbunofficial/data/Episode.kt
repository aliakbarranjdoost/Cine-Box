package dev.aliakbar.tmdbunofficial.data

data class Episode(
    val id: Int,
    val name: String,
    val overview: String,
    val voteAverage: Float,
    val voteCount: Int,
    val airDate: String,
    val runtime: Int?,
    val seasonNumber: Int,
    val episodeNumber: Int,
    val episodeType: String,
    val productionCode: String,
    val showId: Int,
    val stillPath: String?,
)
