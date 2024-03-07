package dev.aliakbar.tmdbunofficial.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.aliakbar.tmdbunofficial.util.LOCAL_BOOKMARK_TABLE_NAME

@Entity(tableName = LOCAL_BOOKMARK_TABLE_NAME)
data class LocalBookmark(
    @PrimaryKey
    val id: Int,
    val title: String,
    val score: Float,
    val poster: String,
    val backdrop: String,
    val type: String
)