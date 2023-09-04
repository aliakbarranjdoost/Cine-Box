package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkResponse<T>(
    val page: Int,
    val results: T,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)

@Serializable
data class NetworkVideoResponse(
    val results: List<NetworkVideo>
)

@Serializable
data class NetworkImageResponse(
    val backdrops: List<NetworkImage>,
    val logos: List<NetworkImage>,
    val posters: List<NetworkImage>
)