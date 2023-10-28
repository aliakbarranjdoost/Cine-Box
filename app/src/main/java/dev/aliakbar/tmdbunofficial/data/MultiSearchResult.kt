package dev.aliakbar.tmdbunofficial.data

data class MultiSearchResult(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val originalLanguage: String,
    val backdropPath: String? = null,
    val posterPath: String? = null,
    val mediaType: String,
    val genreList: List<Int>,
    val releaseDate: String,
    val adult: Boolean,
    val popularity: Float,
    val voteAverage: Float,
    val voteCount: Int,

    val name: String,
    val originalName: String,
    val gender: Int,
    val knownForDepartment: String,
    val profilePath: String? = null,
    val knownFor: String? = null,

    val originCountryList: List<String>
)
