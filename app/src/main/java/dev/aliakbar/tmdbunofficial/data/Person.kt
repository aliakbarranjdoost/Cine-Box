package dev.aliakbar.tmdbunofficial.data

data class Person(
    val id: Int,
    val creditId: String,
    val name: String,
    val gender: Int,
    val profileUrl: String
)

data class PersonDetails(
    val adult: Boolean,
    val alsoKnownAs: List<String>,
    val biography: String,
    val birthday: String,
    val deathDay: String? = null,
    val gender: Int,
    val homepage: String? = null,
    val id: Int,
    val imdbId : String,
    val knownForDepartment: String,
    val name: String,
    val placeOfBirth: String,
    val popularity: Float,
    val profileUrl: String,
    val asMovieCast: List<PersonMovieAsCast>,
    val asTvCast: List<PersonTvAsCast>,
    val asMovieCrew: List<PersonMovieAsCrew>,
    val asTvCrew: List<PersonTvAsCrew>,
    val images: List<Image>
)