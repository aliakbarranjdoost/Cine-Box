package dev.aliakbar.tmdbunofficial.data

data class SeasonDetails(
    val id: Int,
    val name: String,
    val overview: String,
    val episodes: List<Episode>,
    val airDate: String?,
    val seasonNumber: Int,
    val voteAverage: Float,
    val posterUrl: String?
)
