package dev.aliakbar.tmdbunofficial.data

data class Crew(
    val id: Int,
    val adult: Boolean,
    val gender: Int,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Float,
    val profilePath: String? = null,
    val creditId: String,
    val department: String,
    val job: String
)
