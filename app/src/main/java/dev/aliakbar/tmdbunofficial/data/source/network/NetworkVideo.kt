package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.Serializable

@Serializable
data class NetworkVideo(
    val name: String,
    val key: String,
    val site: String,
    val type: String,
    val official: Boolean,
    /*@SerialName("iso_639_1")
    val iso6391: String,
    @SerialName("iso_3166_1")
    val iso31661: String,
    val size: Int,
    @SerialName("published_at")
    val publishedAt: String,
    val id: String*/
)
