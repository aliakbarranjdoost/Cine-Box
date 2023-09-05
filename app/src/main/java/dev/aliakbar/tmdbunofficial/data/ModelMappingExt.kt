package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.LocalImageConfiguration
import dev.aliakbar.tmdbunofficial.data.source.local.LocalTrend
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkCast
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkCollection
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkCompany
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkCountry
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkCrew
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkGenre
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkImage
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkImageConfiguration
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkLanguage
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkMovieDetails
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkPopularMovie
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkPopularSerial
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkTrendingMovie
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkTrendingSeries
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkVideo

fun NetworkImageConfiguration.toExternal() = ImageConfiguration(
    baseUrl = baseUrl,
    secureBaseUrl = secureBaseUrl,
    backdropSizes = backdropSizes,
    logoSizes = logoSizes,
    posterSizes = posterSizes,
    profileSizes = profileSizes,
    stillSizes = stillSizes
)

fun NetworkImageConfiguration.toLocal(id: Int) = LocalImageConfiguration(
    id = id,
    baseUrl = baseUrl,
    secureBaseUrl = secureBaseUrl,
    backdropSizes = backdropSizes,
    logoSizes = logoSizes,
    posterSizes = posterSizes,
    profileSizes = profileSizes,
    stillSizes = stillSizes
)

fun NetworkTrendingMovie.toExternal(basePosterUrl: String) = Trend(
    id = id,
    title = title,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    rank = 0
)

@JvmName("trendingMovieNetworkToExternal")
fun List<NetworkTrendingMovie>.toExternal(basePosterUrl: String) =
    map { it.toExternal(basePosterUrl) }

fun NetworkTrendingMovie.toLocal(basePosterUrl: String, rank: Int) = LocalTrend(
    id = id,
    title = title,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    rank = rank
)

@JvmName("trendingMovieNetworkToLocal")
fun List<NetworkTrendingMovie>.toLocal(basePosterUrl: String) = mapIndexed()
{ index, networkTrendMovie ->
    networkTrendMovie.toLocal(basePosterUrl, index.inc())
}

fun LocalTrend.toExternal() = Trend(
    id = id,
    title = title,
    score = score,
    poster = poster,
    rank = rank
)

@JvmName("trendingSerialLocalToExternal")
fun List<LocalTrend>.toExternal() = map()
{ localTrend ->
    localTrend.toExternal()
}

fun NetworkTrendingSeries.toExternal(basePosterUrl: String) = Trend(
    id = id,
    title = name,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    rank = 0
)

@JvmName("trendingSerialNetworkToExternal")
fun List<NetworkTrendingSeries>.toExternal(basePosterUrl: String) =
    map { it.toExternal(basePosterUrl) }

fun NetworkTrendingSeries.toLocal(basePosterUrl: String, rank: Int) = LocalTrend(
    id = id,
    title = name,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    rank = rank
)

@JvmName("trendingSerialNetworkToLocal")
fun List<NetworkTrendingSeries>.toLocal(basePosterUrl: String) = mapIndexed()
{ index, networkTrendMovie ->
    networkTrendMovie.toLocal(basePosterUrl, index.inc())
}

fun NetworkPopularMovie.toExternal(basePosterUrl: String) = Trend(
    id = id,
    title = title,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    rank = 0
)

@JvmName("popularMovieNetworkToExternal")
fun List<NetworkPopularMovie>.toExternal(basePosterUrl: String) =
    map { it.toExternal(basePosterUrl) }

fun NetworkPopularMovie.toLocal(basePosterUrl: String, rank: Int) = LocalTrend(
    id = id,
    title = title,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    rank = rank
)

@JvmName("popularMovieNetworkToLocal")
fun List<NetworkPopularMovie>.toLocal(basePosterUrl: String) = mapIndexed()
{ index, networkPopularMovie ->
    networkPopularMovie.toLocal(basePosterUrl, index.inc())
}

fun NetworkPopularSerial.toExternal(basePosterUrl: String) = Trend(
    id = id,
    title = name,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    rank = 0
)

@JvmName("popularSerialNetworkToExternal")
fun List<NetworkPopularSerial>.toExternal(basePosterUrl: String) =
    map { it.toExternal(basePosterUrl) }

fun NetworkPopularSerial.toLocal(basePosterUrl: String, rank: Int) = LocalTrend(
    id = id,
    title = name,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    rank = rank
)

@JvmName("popularSerialNetworkToLocal")
fun List<NetworkPopularSerial>.toLocal(basePosterUrl: String) = mapIndexed()
{ index, networkPopularMovie ->
    networkPopularMovie.toLocal(basePosterUrl, index.inc())
}

fun NetworkMovieDetails.toExternal(
    basePosterUrl: String,
    baseBackdropUrl: String,
    baseLogoUrl: String,
    baseProfileUrl: String) = Movie(
    id = id,
    title = title,
    originalTitle = originalTitle,
    tagline = tagline,
    overview = overview,
    originalLanguage = originalLanguage,
    releaseDate = releaseDate,
    adult = adult,
    popularity = popularity,
    voteAverage = voteAverage,
    voteCount = voteCount,
    budget = budget,
    homepage = homepage,
    revenue = revenue,
    runtime = runtime,
    status = status,
    backdropPath = baseBackdropUrl + backdropPath,
    posterPath = basePosterUrl + posterPath,
    productionCountries = productionCountries.toExternal(),
    collection = collection?.toExternal(),
    genres = genres.toExternal(),
    spokenLanguages = spokenLanguages.toExternal(),
    productionCompanies = productionCompanies.toExternal(),
    casts = credits.cast.toExternal(baseProfileUrl),
    crews = credits.crew.toExternal(baseProfileUrl),
    videos = videos.results.toExternal(),
    posters = images.posters.toExternal(basePosterUrl),
    backdrops = images.backdrops.toExternal(baseBackdropUrl),
    logos = images.logos.toExternal(baseLogoUrl),
    recommendations = recommendations.results.toExternal(basePosterUrl)
)

fun NetworkGenre.toExternal() = Genre(
    id = id,
    name = name
)

@JvmName("NetworkGenreToExternal")
fun List<NetworkGenre>.toExternal() = map()
{
    networkGenre -> networkGenre.toExternal()
}

fun NetworkCompany.toExternal() = Company(
    id = id,
    logoPath = logoPath,
    name = name,
    originCountry = originCountry
)

@JvmName("NetworkCompanyToExternal")
fun List<NetworkCompany>.toExternal() = map()
{
    networkCompany -> networkCompany.toExternal()
}

fun NetworkCollection.toExternal() = Collection(
    id = id,
    name = name,
    posterPath = posterPath,
    backdropPath = backdropPath
)

fun NetworkCountry.toExternal() = Country(
    iso = iso,
    name = name
)

@JvmName("NetworkCountryToExternal")
fun List<NetworkCountry>.toExternal() = map()
{
        networkCountry -> networkCountry.toExternal()
}

fun NetworkLanguage.toExternal() = Language(
    iso = iso,
    englishName = englishName,
    name = name
)

@JvmName("NetworkLanguageToExternal")
fun List<NetworkLanguage>.toExternal() = map()
{
        networkLanguage -> networkLanguage.toExternal()
}

fun NetworkCast.toExternal(baseProfileUrl: String) = Cast(
    id = id,
    adult = adult,
    gender = gender,
    knownFor = knownFor,
    name = name,
    originalName = originalName,
    popularity = popularity,
    profilePath = baseProfileUrl + profilePath,
    castId = castId,
    character = character,
    creditId = creditId,
    order = order
)

@JvmName("NetworkCastToExternal")
fun List<NetworkCast>.toExternal(baseProfileUrl: String) = map()
{
    networkCast -> networkCast.toExternal(baseProfileUrl)
}

fun NetworkCrew.toExternal(baseProfileUrl: String) = Crew(
    id = id,
    adult = adult,
    gender = gender,
    knownForDepartment = knownForDepartment,
    name = name,
    originalName = originalName,
    popularity = popularity,
    profilePath = baseProfileUrl + profilePath,
    creditId = creditId,
    department = department,
    job = job
)

@JvmName("NetworkCrewToExternal")
fun List<NetworkCrew>.toExternal(baseProfileUrl: String) = map()
{
    networkCrew -> networkCrew.toExternal(baseProfileUrl)
}

fun NetworkVideo.toExternal() = Video(
    iso6391 = iso6391,
    iso31661 = iso31661,
    name = name,
    key = key,
    site = site,
    size = size,
    type = type,
    official = official,
    publishedAt = publishedAt,
    id = id
)

@JvmName("NetworkVideoToExternal")
fun List<NetworkVideo>.toExternal() = map()
{
    networkVideo -> networkVideo.toExternal()
}

fun NetworkImage.toExternal(baseUrl: String) = Image(
    iso6391 = iso6391,
    aspectRatio = aspectRatio,
    height = height,
    width = width,
    filePath = baseUrl + filePath,
    voteAverage = voteAverage,
    voteCount = voteCount
)

@JvmName("NetworkImageToExternal")
fun List<NetworkImage>.toExternal(baseUrl: String) = map()
{
    networkImage -> networkImage.toExternal(baseUrl)
}