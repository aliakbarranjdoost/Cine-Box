package dev.aliakbar.tmdbunofficial.data

data class Crew(
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val profileUrl: String? = null,
    val department: String,
    val job: String
)
