package dev.aliakbar.tmdbunofficial.data

import dev.aliakbar.tmdbunofficial.data.source.local.LocalBookmark
import dev.aliakbar.tmdbunofficial.data.source.local.LocalImageConfiguration
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkCast
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkCollection
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkCompany
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkCountry
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkCreatedBy
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkCrew
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkEpisode
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkEpisodeDetails
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkGenre
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkImage
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkImageConfiguration
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkLanguage
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkMovieDetails
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkMultiSearchResult
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkPersonDetails
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkPersonMoviesAndTvs
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkPopularMovie
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkPopularSerial
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkSeason
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkSeasonDetails
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkTrendingMovie
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkTrendingSeries
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkTvDetails
import dev.aliakbar.tmdbunofficial.data.source.network.NetworkVideo
import dev.aliakbar.tmdbunofficial.util.mergeSimilarItems
import dev.aliakbar.tmdbunofficial.util.separateMoviesAndTvsCast
import dev.aliakbar.tmdbunofficial.util.separateMoviesAndTvsCrew

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
    rank: Int = 0
) = Trend(
    id = id,
    title = title,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    backdrop = baseBackdropUrl + backdropPath,
    rank = rank,
    type = mediaType ?: this.mediaType!!
)

@JvmName("trendingMovieNetworkToExternal")
fun List<NetworkTrendingMovie>.toExternal(
    basePosterUrl: String,
    baseBackdropUrl: String,
    isBookmark: Boolean
) = map { it.toExternal(basePosterUrl, baseBackdropUrl) }

fun NetworkTrendingSeries.toExternal(
    basePosterUrl: String, baseBackdropUrl: String,
    mediaType: String? = null,
    rank: Int = 0
) = Trend(
    id = id,
    title = name,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    backdrop = baseBackdropUrl + backdropPath,
    rank = rank,
    type = mediaType ?: this.mediaType!!
)

@JvmName("trendingSerialNetworkToExternal")
fun List<NetworkTrendingSeries>.toExternal(
    basePosterUrl: String, baseBackdropUrl: String,
    isBookmark: Boolean
) = map { it.toExternal(basePosterUrl, baseBackdropUrl) }

fun NetworkPopularMovie.toExternal(
    basePosterUrl: String,
    baseBackdropUrl: String,
    rank: Int
) = Trend(
    id = id,
    title = title,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    backdrop = baseBackdropUrl + backdropPath,
    rank = rank,
    type = "movie"
)

@JvmName("popularMovieNetworkToExternal")
fun List<NetworkPopularMovie>.toExternal(
    basePosterUrl: String, baseBackdropUrl: String,
) = mapIndexed()
{ index, networkPopularMovie ->
    networkPopularMovie.toExternal(basePosterUrl, baseBackdropUrl, index.inc())
}

fun NetworkPopularSerial.toExternal(
    basePosterUrl: String,
    baseBackdropUrl: String,
    rank: Int
) = Trend(
    id = id,
    title = name,
    score = voteAverage,
    poster = basePosterUrl + posterPath,
    backdrop = baseBackdropUrl + backdropPath,
    rank = rank,
    type = "tv"
)

@JvmName("popularSerialNetworkToExternal")
fun List<NetworkPopularSerial>.toExternal(
    basePosterUrl: String, baseBackdropUrl: String,
    isBookmark: Boolean
) = mapIndexed()
{ index, networkPopularSerial ->
    networkPopularSerial.toExternal(basePosterUrl, baseBackdropUrl, index)
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
    casts = mergeSimilarItems(credits.cast.toPerson(baseProfileUrl)),
    crews = mergeSimilarItems(credits.crew.toPerson(baseProfileUrl)),
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
//    adult = adult,
//    gender = gender,
//    knownFor = knownFor,
    name = name,
//    originalName = originalName,
//    popularity = popularity,
    profileUrl = baseProfileUrl + profilePath,
//    castId = castId,
    character = character,
//    creditId = creditId,
//    order = order
)

@JvmName("NetworkCastToExternal")
fun List<NetworkCast>.toExternal(baseProfileUrl: String) = map()
{ networkCast ->
    networkCast.toExternal(baseProfileUrl)
}

fun NetworkCrew.toExternal(baseProfileUrl: String) = Crew(
    id = id,
//    adult = adult,
//    gender = gender,
    knownForDepartment = knownForDepartment,
    name = name,
//    originalName = originalName,
//    popularity = popularity,
    profileUrl = baseProfileUrl + profilePath,
//    creditId = creditId,
    department = department,
    job = job
)

@JvmName("NetworkCrewToExternal")
fun List<NetworkCrew>.toExternal(baseProfileUrl: String) = map()
{ networkCrew ->
    networkCrew.toExternal(baseProfileUrl)
}

fun NetworkVideo.toExternal() = Video(
//    iso6391 = iso6391,
//    iso31661 = iso31661,
    name = name,
    key = key,
    site = site,
//    size = size,
    type = type,
    official = official,
//    publishedAt = publishedAt,
//    id = id
)

@JvmName("NetworkVideoToExternal")
fun List<NetworkVideo>.toExternal() = map()
{ networkVideo ->
    networkVideo.toExternal()
}

fun NetworkImage.toExternal(baseUrl: String) = Image(
//    iso6391 = iso6391,
//    aspectRatio = aspectRatio,
//    height = height,
//    width = width,
    fileUrl = baseUrl + filePath,
//    voteAverage = voteAverage,
//    voteCount = voteCount
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
) = when (val mediaType = MediaType.valueOf(mediaType.uppercase()))
{
    MediaType.MOVIE  -> SearchResult(
        id = id,
        title = title!!,
        posterUrl = if (posterPath != null) basePosterUrl + posterPath else "",
        mediaType = mediaType,
        releaseDate = releaseDate!!,
        knownForDepartment = null,
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
        backdropUrl = "",
        score = null
    )
}

@JvmName("NetworkMultiSearchResultToExternal")
fun List<NetworkMultiSearchResult>.toExternal(
    basePosterUrl: String,
    baseBackdropUrl: String,
    baseProfileUrl: String,
) = map { it.toExternal(basePosterUrl, baseProfileUrl,baseProfileUrl) }

fun NetworkTvDetails.toExternal(
    basePosterUrl: String,
    baseBackdropUrl: String,
    baseLogoUrl: String,
    baseProfileUrl: String,
    isBookmark: Boolean
) = Tv(
    id = id,
    name = name,
//    originalName = originalName,
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
//    productionCountries = productionCountries.toExternal(),
    genres = genres.toExternal(),
//    spokenLanguages = spokenLanguages.toExternal(),
//    productionCompanies = productionCompanies.toExternal(baseLogoUrl),
    casts = mergeSimilarItems(credits.cast.toPerson(baseProfileUrl)),
    crews = mergeSimilarItems(credits.crew.toPerson(baseProfileUrl)),
    videos = videos.results.toExternal(),
    posters = images.posters.toExternal(basePosterUrl),
    backdrops = images.backdrops.toExternal(baseBackdropUrl),
//    logos = images.logos.toExternal(baseLogoUrl),
    recommendations = recommendations.results.toExternal(
        basePosterUrl, baseBackdropUrl, false
    ),
    createdBy = createdBy.toExternal(baseProfileUrl),
    firstAirDate = firstAirDate,
    isInProduction = isInProduction,
//    languages = languages,
    lastAirDate = lastAirDate,
//    lastEpisodeToAir = lastEpisodeToAir,
//    networks = networks.toExternal(baseLogoUrl),
//    nextEpisodeToAir = nextEpisodeToAir,
    numberOfEpisodes = numberOfEpisodes,
    numberOfSeasons = numberOfSeasons,
//    originCountry = originCountry,
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
//    _id,
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
//    airDate,
//    runtime,
    seasonNumber,
    episodeNumber,
    episodeType,
//    productionCode,
    showId,
    baseStillUrl + stillPath
)

@JvmName("NetworkEpisodeToExternal")
fun List<NetworkEpisode>.toExternal(baseStillPath: String) = map { it.toExternal(baseStillPath) }

fun NetworkCreatedBy.toExternal(baseProfileUrl: String) = CreatedBy(
    id, creditId, name, gender, baseProfileUrl + profilePath
)

@JvmName("NetworkPersonToExternal")
fun List<NetworkCreatedBy>.toExternal(baseProfileUrl: String) = map { it.toExternal(baseProfileUrl) }

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
//    productionCode = productionCode,
    runtime = runtime,
    seasonNumber = seasonNumber,
    stillUrl = baseStillUrl + stillPath,
    stills = images.stills.toExternal(baseStillUrl),
    casts = mergeSimilarItems(credits.cast.toPerson(baseProfileUrl)),
    crews = mergeSimilarItems(credits.crew.toPerson(baseProfileUrl)),
    guestStars = mergeSimilarItems(credits.guestStars.toPerson(baseProfileUrl)),
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

fun NetworkPersonDetails.toExternal(
    baseProfileUrl: String,
    baseBackdropUrl: String,
    basePosterUrl: String,
) = PersonDetails(
    adult = adult,
    alsoKnownAs = alsoKnownAs,
    biography =biography,
    birthday = birthday,
    deathDay = deathDay,
    gender = gender,
    homepage = homepage,
    id = id,
    imdbId = imdbId,
    knownForDepartment = knownForDepartment,
    name = name,
    placeOfBirth = placeOfBirth,
    popularity = popularity,
    profileUrl = baseProfileUrl + profilePath,
    images = images.profiles.toExternal(baseProfileUrl),
    // TODO: find a way to call this method once
    asMovieCast = combinedCredits.cast.separateMoviesAndTvsCast(baseBackdropUrl, basePosterUrl).first,
    asTvCast = combinedCredits.cast.separateMoviesAndTvsCast(baseBackdropUrl, basePosterUrl).second,
    asMovieCrew = combinedCredits.crew.separateMoviesAndTvsCrew(baseBackdropUrl, basePosterUrl).first,
    asTvCrew = combinedCredits.crew.separateMoviesAndTvsCrew(baseBackdropUrl, basePosterUrl).second
)

fun NetworkPersonMoviesAndTvs.toExternalMovieCast(
    baseBackdropUrl: String,
    basePosterUrl: String,
) = PersonMovieAsCast(
//    adult = adult,
    backdropUrl = baseBackdropUrl + backdropPath,
    id = id,
//    originalLanguage = originalLanguage,
//    overview = overview,
//    popularity = popularity,
    posterUrl = basePosterUrl + posterPath,
    voteAverage = voteAverage,
    voteCount = voteCount,
//    originalTitle = originalTitle,
//    releaseDate = releaseDate,
    title = title!!,
//    video = video,
    character = character!!,
//    creditId = creditId,
//    order = order
)

fun NetworkPersonMoviesAndTvs.toExternalTvCast(
    baseBackdropUrl: String,
    basePosterUrl: String,
) = PersonTvAsCast(
//    adult = adult,
    backdropUrl = baseBackdropUrl + backdropPath,
    id = id,
//    originalLanguage = originalLanguage,
//    overview = overview,
//    popularity = popularity,
    posterUrl = basePosterUrl + posterPath,
    voteAverage = voteAverage,
    voteCount = voteCount,
//    originCountry = originCountries,
//    originalName = originalName,
    firstAirDate = firstAirDate,
    name = name!!,
    character = character!!,
//    creditId = creditId,
//    episodeCount = episodeCount
)

fun NetworkPersonMoviesAndTvs.toExternalMovieCrew(
    baseBackdropUrl: String,
    basePosterUrl: String,
) = PersonMovieAsCrew(
//    adult = adult,
    backdropUrl = baseBackdropUrl + backdropPath,
    id = id,
//    originalLanguage = originalLanguage,
//    overview = overview,
//    popularity = popularity,
    posterUrl = basePosterUrl + posterPath,
    voteAverage = voteAverage,
    voteCount = voteCount,
//    originalTitle = originalTitle,
    releaseDate = releaseDate,
    title = title!!,
//    video = video,
//    creditId = creditId,
    department = department!!,
    job = job!!
)

fun NetworkPersonMoviesAndTvs.toExternalTvCrew(
    baseBackdropUrl: String,
    basePosterUrl: String,
) = PersonTvAsCrew(
//    adult = adult,
    backdropUrl = baseBackdropUrl + backdropPath,
    id = id,
//    originalLanguage = originalLanguage,
//    overview = overview,
//    popularity = popularity,
    posterUrl = basePosterUrl + posterPath,
    voteAverage = voteAverage,
    voteCount = voteCount,
//    originCountry = originCountries,
//    originalName = originalName,
    firstAirDate = firstAirDate,
    name = name!!,
//    creditId = creditId,
//    episodeCount = episodeCount,
    job = job!!,
    department = department!!
)
/*

fun PersonMovieAsCast.toTrend() = Trend(
    id = id,
    title = title,
    score = voteAverage ?: 0.0f,
    poster = posterUrl,
    backdrop = backdropUrl,
    rank = 0,
    isBookmark = false,
    type = "movie"
)

fun PersonTvAsCast.toTrend() = Trend(
    id = id,
    title = name,
    score = voteAverage ?: 0.0f,
    poster = posterUrl,
    backdrop = backdropUrl,
    rank = 0,
    isBookmark = false,
    type = "movie"
)

fun PersonMovieAsCrew.toTrend() = Trend(
    id = id,
    title = title,
    score = voteAverage ?: 0.0f,
    poster = posterUrl,
    backdrop = backdropUrl,
    rank = 0,
    isBookmark = false,
    type = "movie"
)

fun PersonTvAsCrew.toTrend() = Trend(
    id = id,
    title = name,
    score = voteAverage ?: 0.0f,
    poster = posterUrl,
    backdrop = backdropUrl,
    rank = 0,
    isBookmark = false,
    type = "movie"
)

*/

fun NetworkCast.toPerson(baseProfileUrl: String) = Person(
    id = id,
    name = name,
    role = character,
    profileUrl = baseProfileUrl + profilePath
)

@JvmName("NetworkCastToPeople")
fun List<NetworkCast>.toPerson(baseProfileUrl: String) = map { it.toPerson(baseProfileUrl) }

fun NetworkCrew.toPerson(baseProfileUrl: String) = Person(
    id = id,
    name = name,
    role = department,
    profileUrl = baseProfileUrl + profilePath
)

@JvmName("NetworkCrewToPeople")
fun List<NetworkCrew>.toPerson(baseProfileUrl: String) = map { it.toPerson(baseProfileUrl) }
