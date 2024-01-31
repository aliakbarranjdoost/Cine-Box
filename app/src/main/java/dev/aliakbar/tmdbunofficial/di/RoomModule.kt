package dev.aliakbar.tmdbunofficial.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.aliakbar.tmdbunofficial.data.source.local.LocalBookmarkDao
import dev.aliakbar.tmdbunofficial.data.source.local.LocalConfigurationDao
import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule
{
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): TmdbDatabase
    {
        return Room.databaseBuilder(context, TmdbDatabase::class.java, "tmdb_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideConfigurationDao(tmdbDatabase: TmdbDatabase): LocalConfigurationDao
    {
        return tmdbDatabase.configurationDao()
    }

    @Provides
    fun provideBookmarkDao(tmdbDatabase: TmdbDatabase): LocalBookmarkDao
    {
        return tmdbDatabase.bookmarkDao()
    }
}