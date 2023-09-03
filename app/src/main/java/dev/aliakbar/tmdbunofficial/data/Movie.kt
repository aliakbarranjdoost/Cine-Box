package dev.aliakbar.tmdbunofficial.data

data class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val tagline: String,
    val overview: String,
    val originalLanguage: String,
    val backdropPath: String,
    val posterPath: String,
    val genres: List<Genre>,
    val releaseDate: String,
    val adult: Boolean,
    val popularity: Float,
    val voteAverage: Float,
    val voteCount: Int,
    val budget: Int,
    val homepage: String,
    val productionCompanies: List<Company>,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val collection: Collection?,
    val productionCountries: List<Country>,
    val spokenLanguages: List<Language>,
    val casts: List<Cast>,
    val crews: List<Crew>
)
