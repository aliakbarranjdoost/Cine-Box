package dev.aliakbar.tmdbunofficial.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trends")
data class LocalTrend(
    @PrimaryKey
    val id: Int,
    val title: String,
    val score: Float,
    val poster: String,
    val rank: Int
)
