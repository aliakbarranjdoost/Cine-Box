package dev.aliakbar.tmdbunofficial.data

data class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val tagline: String,
    val overview: String,
    val originalLanguage: String,
    val backdropUrl: String,
    val posterUrl: String,
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
    val isBookmark: Boolean,
    val productionCountries: List<Country>,
    val spokenLanguages: List<Language>,
    val casts: List<Person>,
    val crews: List<Person>,
    val videos: List<Video>,
    val posters: List<Image>,
    val backdrops: List<Image>,
    val logos: List<Image>,
    val recommendations: List<Trend>
)
