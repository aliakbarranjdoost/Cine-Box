package dev.aliakbar.tmdbunofficial.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface LocalConfigurationDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(configuration: LocalConfiguration)

    @Update
    suspend fun update(configuration: LocalConfiguration)

    @Delete
    suspend fun delete(configuration: LocalConfiguration)

    @Query("SELECT * from configurations WHERE id = :id")
    fun getConfiguration(id: Int): LocalConfiguration

    @Query("SELECT * from configurations")
    fun getAllConfigurations(): List<LocalConfiguration>
}