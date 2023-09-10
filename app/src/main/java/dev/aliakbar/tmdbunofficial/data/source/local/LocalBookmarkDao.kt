package dev.aliakbar.tmdbunofficial.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalBookmarkDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmark: LocalBookmark)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(bookmark: List<LocalBookmark>)

    @Update
    suspend fun update(bookmark: LocalBookmark)

    @Delete
    suspend fun delete(bookmark: LocalBookmark)

    @Query("SELECT * FROM bookmarks WHERE id = :id")
    fun getBookmark(id: Int): Flow<LocalBookmark>

    @Query("SELECT * FROM bookmarks")
    fun getAllBookmarks(): Flow<List<LocalBookmark>>
}