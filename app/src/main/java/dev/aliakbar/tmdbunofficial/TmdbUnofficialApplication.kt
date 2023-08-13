package dev.aliakbar.tmdbunofficial

import android.app.Application

class TmdbUnofficialApplication: Application()
{
    lateinit var container: AppContainer

    override fun onCreate()
    {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}