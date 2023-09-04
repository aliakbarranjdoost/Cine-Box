package dev.aliakbar.tmdbunofficial.data

data class Image(
    val iso6391 : String? = null,
    val aspectRatio: Float,
    val height: Int,
    val width: Int,
    val filePath: String,
    val voteAverage: Float,
    val voteCount: Int
)
