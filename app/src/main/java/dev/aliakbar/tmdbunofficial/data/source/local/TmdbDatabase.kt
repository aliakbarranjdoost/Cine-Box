package dev.aliakbar.tmdbunofficial.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.aliakbar.tmdbunofficial.util.Converters

@Database(
    entities = [
        LocalImageConfiguration::class,
        LocalBookmark::class
    ],
    version = 7,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class TmdbDatabase: RoomDatabase()
{
    abstract fun configurationDao(): LocalConfigurationDao
    abstract fun bookmarkDao(): LocalBookmarkDao
}