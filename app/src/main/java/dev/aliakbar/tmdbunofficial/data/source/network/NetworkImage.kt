package dev.aliakbar.tmdbunofficial.data.source.network

import dev.aliakbar.tmdbunofficial.util.NETWORK_FILE_PATH_SERIAL_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkImage(
    @SerialName(NETWORK_FILE_PATH_SERIAL_NAME)
    val filePath: String,
    /*@SerialName("iso_639_1")
    val iso6391 : String? = null,
    @SerialName("aspect_ratio")
    val aspectRatio: Float,
    val height: Int,
    val width: Int,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("vote_count")
    val voteCount: Int*/
)
