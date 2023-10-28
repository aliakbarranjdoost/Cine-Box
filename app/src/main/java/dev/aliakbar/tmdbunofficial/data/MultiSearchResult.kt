package dev.aliakbar.tmdbunofficial.data

data class MultiSearchResult(
    val id: Int,
    val title: String?,
    val originalTitle: String?,
    val overview: String?,
    val originalLanguage: String?,
    val backdropPath: String?,
    val posterPath: String?,
    val mediaType: String,
    val genreList: List<Int>?,
    val releaseDate: String?,
    val adult: Boolean,
    val popularity: Float,
    val voteAverage: Float?,
    val voteCount: Int?,

    val name: String?,
    val originalName: String?,
    val gender: Int?,
    val knownForDepartment: String?,
    val profilePath: String?,
    val knownFor: String?,

    val originCountryList: List<String>?
)
