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
    suspend fun insert(configuration: LocalImageConfiguration)

    @Update
    suspend fun update(configuration: LocalImageConfiguration)

    @Delete
    suspend fun delete(configuration: LocalImageConfiguration)

    @Query("SELECT * from configurations WHERE id = :id")
    suspend fun getConfiguration(id: Int = 1): LocalImageConfiguration

    @Query("SELECT * from configurations")
    fun getAllConfigurations(): List<LocalImageConfiguration>
}