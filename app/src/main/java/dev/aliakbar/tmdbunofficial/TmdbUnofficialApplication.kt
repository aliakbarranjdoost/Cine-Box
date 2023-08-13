package dev.aliakbar.tmdbunofficial

import android.app.Application
import dev.aliakbar.tmdbunofficial.data.AppContainer
import dev.aliakbar.tmdbunofficial.data.DefaultAppContainer

class TmdbUnofficialApplication: Application()
{
    lateinit var container: AppContainer

    override fun onCreate()
    {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}