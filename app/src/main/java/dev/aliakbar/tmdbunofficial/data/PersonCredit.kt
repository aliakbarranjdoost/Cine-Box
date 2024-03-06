package dev.aliakbar.tmdbunofficial.data

data class PersonMovieAsCast(
    val backdropUrl: String,
    val id: Int,
    val posterUrl: String,
    val voteAverage: Float?,
    val voteCount: Int?,
    val title: String,
    val character: String
)

data class PersonTvAsCast(
    val backdropUrl: String,
    val id: Int,
    val posterUrl: String,
    val voteAverage: Float?,
    val voteCount: Int?,
    val firstAirDate: String?,
    val name: String,
    val character: String,
    val genres: List<Genre>
)

data class PersonMovieAsCrew(
    val backdropUrl: String,
    val id: Int,
    val posterUrl: String,
    val voteAverage: Float?,
    val voteCount: Int?,
    val releaseDate: String?,
    val title: String,
    val department: String,
    val job: String,
)

data class PersonTvAsCrew(
    val backdropUrl: String,
    val id: Int,
    val posterUrl: String,
    val voteAverage: Float?,
    val voteCount: Int?,
    val firstAirDate: String?,
    val name: String,
    val department: String,
    val job: String
)