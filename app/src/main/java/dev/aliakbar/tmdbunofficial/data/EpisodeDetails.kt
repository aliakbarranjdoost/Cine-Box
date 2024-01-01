package dev.aliakbar.tmdbunofficial.data

data class EpisodeDetails(
    val id: Int,
    val name: String,
    val overview: String,
    val voteAverage: Float,
    val voteCount: Int,
    val airDate: String,
    val episodeNumber: Int,
    val episodeType: String,
    val productionCode: String,
    val runtime: Int?,
    val seasonNumber: Int,
    val stillUrl: String?,
    val stills: List<Image>,
    val casts: List<Cast>,
    val crews: List<Crew>,
    val guestStars: List<Cast>,
    val videos: List<Video>
)