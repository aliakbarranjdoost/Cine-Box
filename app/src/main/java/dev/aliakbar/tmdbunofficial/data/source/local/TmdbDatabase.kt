package dev.aliakbar.tmdbunofficial.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.aliakbar.tmdbunofficial.util.Converters

@Database(entities = [LocalConfiguration::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TmdbDatabase: RoomDatabase()
{
    abstract fun configurationDao(): LocalConfigurationDao
    companion object
    {
        @Volatile
        private var Instance: TmdbDatabase? = null

        fun getDatabase(context: Context): TmdbDatabase
        {
            return Instance ?: synchronized(this)
            {
                Room.databaseBuilder(context, TmdbDatabase::class.java, "tmdb_database")
                    .fallbackToDestructiveMigration()
                    .build().also { Instance = it }
            }
        }
    }
}