package dev.aliakbar.tmdbunofficial.data

data class Cast(
    val id: Int,
    val adult: Boolean,
    val gender: Int,
    val knownFor: String,
    val name: String,
    val originalName: String,
    val popularity: Float,
    val profileUrl: String? = null,
    val castId: Int?,
    val character: String,
    val creditId: String,
    val order: Int
)
