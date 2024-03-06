package dev.aliakbar.tmdbunofficial.data

data class SearchResult(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val backdropUrl: String,
    val score: Float?,
    val mediaType: MediaType,
    val releaseDate: String?,
    val knownForDepartment: String?,
)