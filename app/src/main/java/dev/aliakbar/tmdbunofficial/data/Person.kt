package dev.aliakbar.tmdbunofficial.data

data class Person(
    val id: Int,
    val name: String,
    var role: String,
    val profileUrl: String
)

data class CreatedBy(
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
    val birthday: String? = null,
    val deathDay: String? = null,
    val gender: Int,
    val homepage: String? = null,
    val id: Int,
    val imdbId : String? = null,
    val knownForDepartment: String,
    val name: String,
    val placeOfBirth: String? = null,
    val popularity: Float,
    val profileUrl: String,
    val asMovieCast: List<PersonCredit>,
    val asTvCast: List<PersonCredit>,
    val asMovieCrew: List<PersonCredit>,
    val asTvCrew: List<PersonCredit>,
    val images: List<Image>
)