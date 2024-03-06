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

val genre = Genre(
    id = 14,
    name = "Fantasy"
)

val genres = mutableListOf<Genre>().apply { repeat(10) { this.add(genre) } }.toList()

val company = Company(
    id = 12,
    logoUrl = "/5ThIuO93vsk47oexKTSdfKEr7EC.png",
    name = "New Line Cinema",
    originCountry = "US"
)

val companies = mutableListOf<Company>().apply { repeat(10) { this.add(company) } }.toList()

val country = Country(
    iso = "US",
    name = "United States of America"
)

val countries = mutableListOf<Country>().apply { repeat(10) { this.add(country)} }.toList()

val collection = Collection(
    id = 119,
    name = "The Lord of the Rings Collection",
    posterUrl = "/oENY593nKRVL2PnxXsMtlh8izb4.jpg",
    backdropUrl = "/bccR2CGTWVVSZAG0yqmy3DIvhTX.jpg"
)

val language = Language(
    iso = "en",
    englishName = "English",
    name = "English"
)

val languages = mutableListOf<Language>().apply { repeat(10) { this.add(language) } }.toList()

val cast = Person(
    id = 110,
    name = "Viggo Mortensen",
    profileUrl = "/vH5gVSpHAMhDaFWfh0Q7BG61O1y.jpg",
    role = "Aragorn"
)

val casts = mutableListOf<Person>().apply { repeat(10) { this.add(cast) } }.toList()

val crew = Person(
    id = 123,
    name = "Barrie M. Osborne",
    profileUrl = "/xWtXYk6M5NFroddcQDviLlxOnkU.jpg",
    role = "Producer"
)

val crews = mutableListOf<Person>().apply { repeat(10) { this.add(crew) } }.toList()

val video = Video(
    name = "The Two Towers | The Lord of the Rings 4K Ultra HD | Warner Bros. Entertainment",
    key = "nuTU5XcZTLA",
    site = "YouTube",
    type = "Trailer",
    official = true
)

val videos = mutableListOf<Video>().apply { repeat(10) { this.add(video) } }.toList()

val poster = Image(fileUrl = "/6oom5QYQ2yQTMJIbnvbkBL9cHo6.jpg")

val posters = mutableListOf<Image>().apply { repeat(10) { this.add(poster) } }.toList()

val backdrop = Image(fileUrl = "/tqj7NKj11keFuLzPsBDMUq2dOUO.jpg")

val backdrops = mutableListOf<Image>().apply { repeat(10) { this.add(backdrop) } }.toList()

val logo = Image(fileUrl = "/dMAXhf7jVsc8Qsx26wsoOmoQh3r.png")

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