package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkImage(
    @SerialName("iso_639_1")
    val iso6391 : String? = null,
    @SerialName("aspect_ratio")
    val aspectRatio: Float,
    val height: Int,
    val width: Int,
    @SerialName("file_path")
    val filePath: String,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("vote_count")
    val voteCount: Int
)
