package dev.aliakbar.tmdbunofficial.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "configurations")
data class LocalImageConfiguration(
    @PrimaryKey
    val id: Int,
    val baseUrl: String,
    val secureBaseUrl: String,
    val backdropSizes: List<String>,
    val logoSizes: List<String>,
    val posterSizes: List<String>,
    val profileSizes: List<String>,
    val stillSizes: List<String>,
)