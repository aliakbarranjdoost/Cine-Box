package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.LocalBookmark
import dev.aliakbar.tmdbunofficial.data.source.local.LocalImageConfiguration
import dev.aliakbar.tmdbunofficial.data.source.local.LocalTrend
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkCast
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkCollection
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkCompany
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkCountry
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkCrew
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkEpisode
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkEpisodeDetails
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkGenre
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkImage
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkImageConfiguration
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkLanguage
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkMovieDetails
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkMultiSearchResult
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkPerson
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkPopularMovie
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkPopularSerial
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkSeason
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkSeasonDetails
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkTrendingMovie
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkTrendingSeries
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkTvDetails
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

fun NetworkTrendingMovie.toExternal(
    basePosterUrl: String,
    baseBackdropUrl: String,
    isBookmark: Boolean
) = Trend(
    id = id,
    title = title,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    backdrop = baseBackdropUrl + backdropPath,
    rank = 0,
    isBookmark = isBookmark,
    type = mediaType
)

@JvmName("trendingMovieNetworkToExternal")
fun List<NetworkTrendingMovie>.toExternal(
    basePosterUrl: String,
    baseBackdropUrl: String,
    isBookmark: Boolean
) =
    map { it.toExternal(basePosterUrl, baseBackdropUrl, isBookmark) }

fun NetworkTrendingMovie.toLocal(
    basePosterUrl: String, baseBackdropUrl: String,
    isBookmark: Boolean, rank: Int
) = LocalTrend(
    id = id,
    title = title,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    backdrop = baseBackdropUrl + backdropPath,
    rank = rank,
    isBookmark = isBookmark,
    type = mediaType
)

@JvmName("trendingMovieNetworkToLocal")
fun List<NetworkTrendingMovie>.toLocal(
    basePosterUrl: String, baseBackdropUrl: String,
    isBookmark: Boolean
) = mapIndexed()
{ index, networkTrendMovie ->
    networkTrendMovie.toLocal(basePosterUrl, baseBackdropUrl, isBookmark, index.inc())
}

fun LocalTrend.toExternal() = Trend(
    id = id,
    title = title,
    score = score,
    poster = poster,
    backdrop = backdrop,
    rank = rank,
    isBookmark = isBookmark,
    type = type
)

@JvmName("trendingSerialLocalToExternal")
fun List<LocalTrend>.toExternal() = map()
{ localTrend ->
    localTrend.toExternal()
}

fun NetworkTrendingSeries.toExternal(
    basePosterUrl: String, baseBackdropUrl: String,
    isBookmark: Boolean
) = Trend(
    id = id,
    title = name,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    backdrop = baseBackdropUrl + backdropPath,
    rank = 0,
    isBookmark = isBookmark,
    type = mediaType
)

@JvmName("trendingSerialNetworkToExternal")
fun List<NetworkTrendingSeries>.toExternal(
    basePosterUrl: String, baseBackdropUrl: String,
    isBookmark: Boolean
) =
    map { it.toExternal(basePosterUrl, baseBackdropUrl, isBookmark) }

fun NetworkTrendingSeries.toLocal(
    basePosterUrl: String, baseBackdropUrl: String,
    isBookmark: Boolean, rank: Int
) =
    LocalTrend(
        id = id,
        title = name,
        score = voteAverage,
        poster = basePosterUrl + posterPath,
        backdrop = baseBackdropUrl + backdropPath,
        rank = rank,
        isBookmark = isBookmark,
        type = mediaType
    )

@JvmName("trendingSerialNetworkToLocal")
fun List<NetworkTrendingSeries>.toLocal(
    basePosterUrl: String, baseBackdropUrl: String,
    isBookmark: Boolean
) =
    mapIndexed()
    { index, networkTrendMovie ->
        networkTrendMovie.toLocal(basePosterUrl, baseBackdropUrl, isBookmark, index.inc())
    }

fun NetworkPopularMovie.toExternal(
    basePosterUrl: String, baseBackdropUrl: String,
    rank: Int, isBookmark: Boolean
) = Trend(
    id = id,
    title = title,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    backdrop = baseBackdropUrl + backdropPath,
    rank = rank,
    isBookmark = isBookmark,
    type = "movie"
)

@JvmName("popularMovieNetworkToExternal")
fun List<NetworkPopularMovie>.toExternal(
    basePosterUrl: String, baseBackdropUrl: String,
    isBookmark: Boolean
) = mapIndexed()
{ index, networkPopularMovie ->
    networkPopularMovie.toExternal(
        basePosterUrl, baseBackdropUrl,
        index.inc(), isBookmark,
    )
}

fun NetworkPopularMovie.toLocal(
    basePosterUrl: String, baseBackdropUrl: String,
    isBookmark: Boolean, rank: Int
) =
    LocalTrend(
        id = id,
        title = title,
        score = voteAverage,
        poster = basePosterUrl + posterPath,
        backdrop = baseBackdropUrl + backdropPath,
        rank = rank,
        isBookmark = isBookmark,
        type = "movie"
    )

@JvmName("popularMovieNetworkToLocal")
fun List<NetworkPopularMovie>.toLocal(
    basePosterUrl: String, baseBackdropUrl: String,
    isBookmark: Boolean
) = mapIndexed()
{ index, networkPopularMovie ->
    networkPopularMovie.toLocal(
        basePosterUrl, baseBackdropUrl,
        isBookmark, index.inc()
    )
}

fun NetworkPopularSerial.toExternal(
    basePosterUrl: String,
    baseBackdropUrl: String,
    rank: Int,
    isBookmark: Boolean
) = Trend(
    id = id,
    title = name,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    backdrop = baseBackdropUrl + backdropPath,
    rank = rank,
    isBookmark = isBookmark,
    type = "tv"
)

@JvmName("popularSerialNetworkToExternal")
fun List<NetworkPopularSerial>.toExternal(
    basePosterUrl: String, baseBackdropUrl: String,
    isBookmark: Boolean
) =
    mapIndexed()
    { index, networkPopularSerial ->
        networkPopularSerial.toExternal(basePosterUrl, baseBackdropUrl, index, isBookmark)
    }

fun NetworkPopularSerial.toLocal(
    basePosterUrl: String, baseBackdropUrl: String,
    isBookmark: Boolean, rank: Int
) =
    LocalTrend(
        id = id,
        title = name,
        score = voteAverage,
        poster = basePosterUrl + posterPath,
        backdrop = baseBackdropUrl + backdropPath,
        rank = rank,
        isBookmark = isBookmark,
        type = "tv"
    )

@JvmName("popularSerialNetworkToLocal")
fun List<NetworkPopularSerial>.toLocal(
    basePosterUrl: String, baseBackdropUrl: String,
    isBookmark: Boolean
) =
    mapIndexed()
    { index, networkPopularMovie ->
        networkPopularMovie.toLocal(
            basePosterUrl, baseBackdropUrl,
            isBookmark, index.inc()
        )
    }

fun NetworkMovieDetails.toExternal(
    basePosterUrl: String,
    baseBackdropUrl: String,
    baseLogoUrl: String,
    baseProfileUrl: String,
    isBookmark: Boolean
) = Movie(
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
    backdropUrl = baseBackdropUrl + backdropPath,
    posterUrl = basePosterUrl + posterPath,
    productionCountries = productionCountries.toExternal(),
    collection = collection?.toExternal(),
    genres = genres.toExternal(),
    spokenLanguages = spokenLanguages.toExternal(),
    productionCompanies = productionCompanies.toExternal(baseLogoUrl),
    casts = credits.cast.toExternal(baseProfileUrl),
    crews = credits.crew.toExternal(baseProfileUrl),
    videos = videos.results.toExternal(),
    posters = images.posters.toExternal(basePosterUrl),
    backdrops = images.backdrops.toExternal(baseBackdropUrl),
    logos = images.logos.toExternal(baseLogoUrl),
    recommendations = recommendations.results.toExternal(
        basePosterUrl, baseBackdropUrl, false
    ),
    isBookmark = isBookmark
)

fun NetworkGenre.toExternal() = Genre(
    id = id,
    name = name
)

@JvmName("NetworkGenreToExternal")
fun List<NetworkGenre>.toExternal() = map()
{ networkGenre ->
    networkGenre.toExternal()
}

fun NetworkCompany.toExternal(baseLogoUrl: String) = Company(
    id = id,
    logoUrl = baseLogoUrl + logoPath,
    name = name,
    originCountry = originCountry
)

@JvmName("NetworkCompanyToExternal")
fun List<NetworkCompany>.toExternal(baseLogoUrl: String) = map()
{ networkCompany ->
    networkCompany.toExternal(baseLogoUrl)
}

fun NetworkCollection.toExternal() = Collection(
    id = id,
    name = name,
    posterUrl = posterPath,
    backdropUrl = backdropPath
)

fun NetworkCountry.toExternal() = Country(
    iso = iso,
    name = name
)

@JvmName("NetworkCountryToExternal")
fun List<NetworkCountry>.toExternal() = map()
{ networkCountry ->
    networkCountry.toExternal()
}

fun NetworkLanguage.toExternal() = Language(
    iso = iso,
    englishName = englishName,
    name = name
)

@JvmName("NetworkLanguageToExternal")
fun List<NetworkLanguage>.toExternal() = map()
{ networkLanguage ->
    networkLanguage.toExternal()
}

fun NetworkCast.toExternal(baseProfileUrl: String) = Cast(
    id = id,
    adult = adult,
    gender = gender,
    knownFor = knownFor,
    name = name,
    originalName = originalName,
    popularity = popularity,
    profileUrl = baseProfileUrl + profilePath,
    castId = castId,
    character = character,
    creditId = creditId,
    order = order
)

@JvmName("NetworkCastToExternal")
fun List<NetworkCast>.toExternal(baseProfileUrl: String) = map()
{ networkCast ->
    networkCast.toExternal(baseProfileUrl)
}

fun NetworkCrew.toExternal(baseProfileUrl: String) = Crew(
    id = id,
    adult = adult,
    gender = gender,
    knownForDepartment = knownForDepartment,
    name = name,
    originalName = originalName,
    popularity = popularity,
    profileUrl = baseProfileUrl + profilePath,
    creditId = creditId,
    department = department,
    job = job
)

@JvmName("NetworkCrewToExternal")
fun List<NetworkCrew>.toExternal(baseProfileUrl: String) = map()
{ networkCrew ->
    networkCrew.toExternal(baseProfileUrl)
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
{ networkVideo ->
    networkVideo.toExternal()
}

fun NetworkImage.toExternal(baseUrl: String) = Image(
    iso6391 = iso6391,
    aspectRatio = aspectRatio,
    height = height,
    width = width,
    fileUrl = baseUrl + filePath,
    voteAverage = voteAverage,
    voteCount = voteCount
)

@JvmName("NetworkImageToExternal")
fun List<NetworkImage>.toExternal(baseUrl: String) = map()
{ networkImage ->
    networkImage.toExternal(baseUrl)
}

fun LocalBookmark.toExternal() = Bookmark(
    id = id,
    title = title,
    score = score,
    poster = poster,
    backdropUrl = backdrop,
    type = type
)

@JvmName("LocalBookmarkToExternal")
fun List<LocalBookmark>.toExternal() = map()
{ localBookmark ->
    localBookmark.toExternal()
}

fun Bookmark.toLocal() = LocalBookmark(
    id = id,
    title = title,
    score = score,
    poster = poster,
    backdrop = backdropUrl,
    type = type
)

fun Trend.toBookmark() = Bookmark(
    id = id,
    title = title,
    score = score,
    poster = poster,
    backdropUrl = backdrop,
    type = type
)

fun NetworkMultiSearchResult.toExternal(
    basePosterUrl: String,
    baseBackdropUrl: String,
    baseProfileUrl: String,
    isBookmark: Boolean
) = when (val mediaType = MediaType.valueOf(mediaType.uppercase()))
{
    MediaType.MOVIE  -> SearchResult(
        id = id,
        title = title!!,
        posterUrl = if (posterPath != null) basePosterUrl + posterPath else "",
        mediaType = mediaType,
        releaseDate = releaseDate!!,
        knownForDepartment = null,
        isBookmark = isBookmark,
        backdropUrl = baseBackdropUrl + baseBackdropUrl,
        score = voteAverage!!
    )

    MediaType.TV     -> SearchResult(
        id = id,
        title = name!!,
        posterUrl = if (posterPath != null) basePosterUrl + posterPath else "",
        mediaType = mediaType,
        releaseDate = firstAirDate!!,
        knownForDepartment = null,
        isBookmark = isBookmark ,
        backdropUrl = baseBackdropUrl + baseBackdropUrl,
        score = voteAverage!!
    )

    MediaType.PERSON -> SearchResult(
        id = id,
        title = name!!,
        posterUrl = if (profilePath != null) baseProfileUrl + profilePath else "",
        mediaType = mediaType,
        releaseDate = null,
        knownForDepartment = knownForDepartment,
        isBookmark = isBookmark,
        backdropUrl = "",
        score = null
    )
}

@JvmName("NetworkMultiSearchResultToExternal")
fun List<NetworkMultiSearchResult>.toExternal(
    basePosterUrl: String,
    baseBackdropUrl: String,
    baseProfileUrl: String,
    isBookmark: Boolean
) = map { it.toExternal(basePosterUrl, baseProfileUrl,baseProfileUrl, isBookmark) }

fun NetworkTvDetails.toExternal(
    basePosterUrl: String,
    baseBackdropUrl: String,
    baseLogoUrl: String,
    baseProfileUrl: String,
    isBookmark: Boolean
) = Tv(
    id = id,
    name = name,
    originalName = originalName,
    tagline = tagline,
    overview = overview,
    originalLanguage = originalLanguage,
    adult = adult,
    popularity = popularity,
    voteAverage = voteAverage,
    voteCount = voteCount,
    homepage = homepage,
    status = status,
    backdropUrl = baseBackdropUrl + backdropPath,
    posterUrl = basePosterUrl + posterPath,
    productionCountries = productionCountries.toExternal(),
    genres = genres.toExternal(),
    spokenLanguages = spokenLanguages.toExternal(),
    productionCompanies = productionCompanies.toExternal(baseLogoUrl),
    casts = credits.cast.toExternal(baseProfileUrl),
    crews = credits.crew.toExternal(baseProfileUrl),
    videos = videos.results.toExternal(),
    posters = images.posters.toExternal(basePosterUrl),
    backdrops = images.backdrops.toExternal(baseBackdropUrl),
    logos = images.logos.toExternal(baseLogoUrl),
    recommendations = recommendations.results.toExternal(
        basePosterUrl, baseBackdropUrl, false
    ),
    createdBy = createdBy.toExternal(baseProfileUrl),
    firstAirDate = firstAirDate,
    isInProduction = isInProduction,
    languages = languages,
    lastAirDate = lastAirDate,
    lastEpisodeToAir = lastEpisodeToAir,
    networks = networks.toExternal(baseLogoUrl),
    nextEpisodeToAir = nextEpisodeToAir,
    numberOfEpisodes = numberOfEpisodes,
    numberOfSeasons = numberOfSeasons,
    originCountry = originCountry,
    seasons = seasons.toExternal(basePosterUrl),
    type = type,
    isBookmark = isBookmark
)

fun NetworkSeason.toExternal(basePosterUrl: String) = Season(
    id, name, overview, episodeCount, airDate ?: "UnKnown", seasonNumber, voteAverage, basePosterUrl + posterPath
)

@JvmName("NetworkSeasonToExternal")
fun List<NetworkSeason>.toExternal(basePosterUrl: String) = map { it.toExternal(basePosterUrl) }

fun NetworkSeasonDetails.toExternal(basePosterUrl: String, baseStillUrl: String) = SeasonDetails(
    id,
    _id,
    name,
    overview,
    episodes.toExternal(baseStillUrl),
    airDate,
    seasonNumber,
    voteAverage,
    basePosterUrl + posterPath
)

@JvmName("NetworkSeasonDetailsToExternal")
fun List<NetworkSeasonDetails>.toExternal(basePosterUrl: String, baseStillUrl: String) =
    map { it.toExternal(basePosterUrl, baseStillUrl) }

fun NetworkEpisode.toExternal(baseStillUrl: String) = Episode(
    id,
    name,
    overview,
    voteAverage,
    voteCount,
    airDate,
    runtime,
    seasonNumber,
    episodeNumber,
    episodeType,
    productionCode,
    showId,
    baseStillUrl + stillPath
)

@JvmName("NetworkEpisodeToExternal")
fun List<NetworkEpisode>.toExternal(baseStillPath: String) = map { it.toExternal(baseStillPath) }

fun NetworkPerson.toExternal(baseProfileUrl: String) = Person(
    id, creditId, name, gender, baseProfileUrl + profilePath
)

@JvmName("NetworkPersonToExternal")
fun List<NetworkPerson>.toExternal(baseProfileUrl: String) = map { it.toExternal(baseProfileUrl) }

fun NetworkEpisodeDetails.toExternal(
    baseStillUrl: String,
    baseProfileUrl: String
) = EpisodeDetails(
    id = id,
    name = name,
    overview = overview,
    voteAverage = voteAverage,
    voteCount = voteCount,
    airDate = airDate,
    episodeNumber = episodeNumber,
    episodeType = episodeType,
    productionCode = productionCode,
    runtime = runtime,
    seasonNumber = seasonNumber,
    stillUrl = baseStillUrl + stillPath,
    stills = images.stills.toExternal(baseStillUrl),
    casts = credits.cast.toExternal(baseProfileUrl),
    crews = credits.crew.toExternal(baseProfileUrl),
    guestStars = credits.guestStars.toExternal(baseProfileUrl),
    videos = videos.results.toExternal()
)

fun SearchResult.toBookmark() = Bookmark(
    id, title, score!!, posterUrl, backdropUrl, mediaType.name
)

fun Movie.toBookmark() = Bookmark(
    id = id,
    title = title,
    score = voteAverage,
    poster = posterUrl,
    backdropUrl = backdropUrl,
    type = "movie"
)

fun Tv.toBookmark() = Bookmark(
    id = id,
    title = name,
    score = voteAverage,
    poster = posterUrl,
    backdropUrl = backdropUrl,
    type = "tv"
)