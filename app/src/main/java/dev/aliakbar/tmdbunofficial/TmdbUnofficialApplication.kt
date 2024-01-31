package dev.aliakbar.tmdbunofficial

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TmdbUnofficialApplication: Application()
{
    lateinit var container: AppContainer

    override fun onCreate()
    {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}