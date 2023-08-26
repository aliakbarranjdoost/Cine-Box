package dev.aliakbar.tmdbunofficial.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkLanguage(
    @SerialName("iso_639_1")
    val iso: String,
    @SerialName("english_name")
    val englishName: String,
    val name: String
)
