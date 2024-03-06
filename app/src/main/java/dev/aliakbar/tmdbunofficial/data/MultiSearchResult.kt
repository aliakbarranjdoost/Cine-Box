package dev.aliakbar.tmdbunofficial.data

data class MultiSearchResult(
    val id: Int,
    val title: String?,
    val originalTitle: String?,
    val overview: String?,
    val originalLanguage: String?,
    val backdropUrl: String?,
    val posterUrl: String?,
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
    val profileUrl: String?,
    //val knownFor: String?,

    val originCountryList: List<String>?
)

data class SearchResult(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val backdropUrl: String,
    val score: Float?,
    val mediaType: MediaType,
    val releaseDate: String?,
    val knownForDepartment: String?,
//    val originCountries: List<String>?
)