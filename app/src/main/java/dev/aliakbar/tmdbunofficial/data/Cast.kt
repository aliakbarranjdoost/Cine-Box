package dev.aliakbar.tmdbunofficial.data

data class Cast(
    val id: Int,
    val name: String,
    val profileUrl: String? = null,
    val character: String
)