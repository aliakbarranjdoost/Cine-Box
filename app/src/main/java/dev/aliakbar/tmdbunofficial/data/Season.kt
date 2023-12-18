package dev.aliakbar.tmdbunofficial.data

data class Season(
    val id: Int,
    val name: String,
    val overview: String,
    val episodeCount: Int,
    val airDate: String,
    val seasonNumber: Int,
    val voteAverage: Float,
    val posterPath: String
)
