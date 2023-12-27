package dev.aliakbar.tmdbunofficial.data

data class Bookmark(
    val id: Int,
    val title: String,
    val score: Float,
    val poster: String,
    val backdropUrl: String,
    val type: String
)
