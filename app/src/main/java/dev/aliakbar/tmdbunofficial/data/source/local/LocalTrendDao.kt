package dev.aliakbar.tmdbunofficial.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalTrendDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trend: LocalTrend)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(trends: List<LocalTrend>)

    @Update
    suspend fun update(trend: LocalTrend)

    @Delete
    suspend fun delete(trend: LocalTrend)

    @Query("SELECT * FROM trends WHERE id = :id")
    fun getTrend(id: Int): Flow<LocalTrend>

    @Query("SELECT * FROM trends ORDER BY rank")
    fun getAllTrends(): Flow<List<LocalTrend>>
}