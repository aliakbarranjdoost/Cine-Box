package dev.aliakbar.tmdbunofficial.data.source.sample

import dev.aliakbar.tmdbunofficial.data.Collection
import dev.aliakbar.tmdbunofficial.data.Company
import dev.aliakbar.tmdbunofficial.data.Country
import dev.aliakbar.tmdbunofficial.data.Genre
import dev.aliakbar.tmdbunofficial.data.Language
import dev.aliakbar.tmdbunofficial.data.Movie

val genres = listOf(
    Genre(id = 12, name = "Adventure"),
    Genre(id = 14, name = "Fantasy"),
    Genre(id = 28, name = "Action")
)

val companies = listOf(
    Company(
        id = 12,
        logoPath = "/5ThIuO93vsk47oexKTSdfKEr7EC.png",
        name = "New Line Cinema",
        originCountry = "US"
    ),
    Company(
        id = 11,
        logoPath = "/6FAuASQHybRkZUk08p9PzSs9ezM.png",
        name = "WingNut Films",
        originCountry = "NZ"
    ),
    Company(
        id = 5237,
        logoPath = null,
        name = "The Saul Zaentz Company",
        originCountry = "US"
    )
)
val countries = listOf(
    Country(iso = "NZ", name = "New Zealand"),
    Country(iso = "US", name = "United States of America")
)

val collection = Collection(
    id = 119,
    name = "The Lord of the Rings Collection",
    posterPath = "/oENY593nKRVL2PnxXsMtlh8izb4.jpg",
    backdropPath = "/bccR2CGTWVVSZAG0yqmy3DIvhTX.jpg"
)

val languages = listOf(
    Language(iso = "en", englishName = "English", name = "English")
)

val movie = Movie(
    id = 120,
    title = "The Lord of the Rings: The Fellowship of the Ring",
    originalTitle = "The Lord of the Rings: The Fellowship of the Ring",
    tagline = "One ring to rule them all",
    overview = "Young hobbit Frodo Baggins, after inheriting a mysterious ring from his uncle " +
            "Bilbo, must leave his home in order to keep it from falling into the hands of its" +
            " evil creator. Along the way, a fellowship is formed to protect the ringbearer and" +
            " make sure that the ring arrives at its final destination: Mt. Doom, the only place" +
            " where it can be destroyed.",
    originalLanguage = "en",
    backdropPath = "/tqj7NKj11keFuLzPsBDMUq2dOUO.jpg",
    posterPath = "/6oom5QYQ2yQTMJIbnvbkBL9cHo6.jpg",
    genres = genres,
    releaseDate = "2001-12-18",
    adult = false,
    popularity = 115.997F,
    voteAverage = 8.401F,
    voteCount = 23194,
    budget = 93000000,
    homepage = "http://www.lordoftherings.net/",
    productionCompanies = companies,
    revenue = 871368364,
    runtime = 179,
    status = "Released",
    collection = collection,
    productionCountries = countries,
    spokenLanguages = languages,
    casts = emptyList(),
    crews = emptyList()
)