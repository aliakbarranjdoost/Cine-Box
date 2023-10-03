package dev.aliakbar.tmdbunofficial.data

data class Trend(
    val id: Int,
    val title: String,
    val score: Float,
    val poster: String,
    val backdrop: String,
    val rank: Int
)