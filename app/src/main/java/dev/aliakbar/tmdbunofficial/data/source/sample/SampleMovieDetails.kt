package dev.aliakbar.tmdbunofficial.data.source.sample

import dev.aliakbar.tmdbunofficial.data.Bookmark
import dev.aliakbar.tmdbunofficial.data.Collection
import dev.aliakbar.tmdbunofficial.data.Company
import dev.aliakbar.tmdbunofficial.data.Country
import dev.aliakbar.tmdbunofficial.data.Genre
import dev.aliakbar.tmdbunofficial.data.Image
import dev.aliakbar.tmdbunofficial.data.Language
import dev.aliakbar.tmdbunofficial.data.Movie
import dev.aliakbar.tmdbunofficial.data.Person
import dev.aliakbar.tmdbunofficial.data.Trailer
import dev.aliakbar.tmdbunofficial.data.Trend
import dev.aliakbar.tmdbunofficial.data.Video

val genres = listOf(
    Genre(id = 12, name = "Adventure"),
    Genre(id = 14, name = "Fantasy"),
    Genre(id = 28, name = "Action")
)

val companies = listOf(
    Company(
        id = 12,
        logoUrl = "/5ThIuO93vsk47oexKTSdfKEr7EC.png",
        name = "New Line Cinema",
        originCountry = "US"
    ),
    Company(
        id = 11,
        logoUrl = "/6FAuASQHybRkZUk08p9PzSs9ezM.png",
        name = "WingNut Films",
        originCountry = "NZ"
    ),
    Company(
        id = 5237,
        logoUrl = null,
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
    posterUrl = "/oENY593nKRVL2PnxXsMtlh8izb4.jpg",
    backdropUrl = "/bccR2CGTWVVSZAG0yqmy3DIvhTX.jpg"
)

val languages = listOf(
    Language(iso = "en", englishName = "English", name = "English")
)

val cast = Person(
    id = 110,
//    adult = false,
//    gender = 2,
//    knownFor = "Acting",
    name = "Viggo Mortensen",
//    originalName = "Viggo Mortensen",
//    popularity = 39.757F,
    profileUrl = "/vH5gVSpHAMhDaFWfh0Q7BG61O1y.jpg",
//    castId = 15,
    role = "Aragorn",
//    creditId = "52fe421ac3a36847f8004591",
//    order = 0
)

val casts = mutableListOf<Person>().apply { repeat(10) { this.add(cast) } }.toList()

val crew = Person(
    id = 123,
//    adult = false,
//    gender = 2,
//    knownForDepartment = "Production",
    name = "Barrie M. Osborne",
//    originalName = "Barrie M. Osborne",
//    popularity = 4.655F,
    profileUrl = "/xWtXYk6M5NFroddcQDviLlxOnkU.jpg",
//    creditId = "52fe421ac3a36847f800454f",
//    role = "Production",
    role = "Producer"
)

val crews = mutableListOf<Person>().apply { repeat(10) { this.add(crew) } }.toList()

val video = Video(
//    iso6391 = "en",
//    iso31661 = "US",
    name = "The Two Towers | The Lord of the Rings 4K Ultra HD | Warner Bros. Entertainment",
    key = "nuTU5XcZTLA",
    site = "YouTube",
//    size = 2160,
    type = "Trailer",
    official = true,
//    publishedAt = "2020-12-02T17:34:35.000Z",
//    id = "5fc8aafe3f8ede004000808c"
)

val videos = mutableListOf<Video>().apply { repeat(10) { this.add(video) } }.toList()

val poster = Image(
//    iso6391 = "en",
//    aspectRatio = 0.667F,
//    height = 3000,
//    width = 2000,
    fileUrl = "/6oom5QYQ2yQTMJIbnvbkBL9cHo6.jpg",
//    voteAverage = 6.504F,
//    voteCount = 26
)

val posters = mutableListOf<Image>().apply { repeat(10) { this.add(poster) } }.toList()

val backdrop = Image(
//    iso6391 = null,
//    aspectRatio = 1.778F,
//    height = 1080,
//    width = 1920,
    fileUrl = "/tqj7NKj11keFuLzPsBDMUq2dOUO.jpg",
//    voteAverage = 5.388F,
//    voteCount = 4
)

val backdrops = mutableListOf<Image>().apply { repeat(10) { this.add(backdrop) } }.toList()

val logo = Image(
//    iso6391 = "en",
//    aspectRatio = 4.751F,
//    height = 442,
//    width = 2100,
    fileUrl = "/dMAXhf7jVsc8Qsx26wsoOmoQh3r.png",
//    voteAverage = 0F,
//    voteCount = 0
)

val logos = mutableListOf<Image>().apply { repeat(10) { this.add(logo) } }.toList()

val trend = Trend(
    id = 121,
    title = "The Lord of the Rings: The Two Towers",
    score = 8.384F,
    poster = "/5VTN0pR8gcqV3EPUHHfMGnJYN9L.jpg",
    backdrop = "/bccR2CGTWVVSZAG0yqmy3DIvhTX.jpg",
    rank = 0,
    type = "movie"
)

val recommendations = mutableListOf<Trend>().apply { repeat(10) { this.add(trend)} }.toList()

val bookmark = Bookmark(
    id = 121,
    title = "The Lord of the Rings: The Two Towers",
    score = 8.384F,
    poster = "/5VTN0pR8gcqV3EPUHHfMGnJYN9L.jpg",
    backdropUrl = "/bccR2CGTWVVSZAG0yqmy3DIvhTX.jpg",
    type = "tv"
)

val bookmarks = mutableListOf<Bookmark>().apply { repeat(10) { this.add(bookmark)} }.toList()

val trailer = Trailer(video, trend)

val trailers = mutableListOf<Trailer>().apply { repeat(10) { this.add(trailer)} }.toList()

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
    backdropUrl = "/tqj7NKj11keFuLzPsBDMUq2dOUO.jpg",
    posterUrl = "/6oom5QYQ2yQTMJIbnvbkBL9cHo6.jpg",
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
    casts = casts,
    crews = crews,
    videos = videos,
    posters = posters,
    backdrops = backdrops,
    logos = logos,
    recommendations = recommendations,
    isBookmark = true
)