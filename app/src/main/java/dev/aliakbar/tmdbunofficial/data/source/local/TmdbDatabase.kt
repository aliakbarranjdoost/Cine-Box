package dev.aliakbar.tmdbunofficial.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalBookmark::class],
    version = 8,
    exportSchema = false)
abstract class TmdbDatabase: RoomDatabase()
{
    abstract fun bookmarkDao(): LocalBookmarkDao
}