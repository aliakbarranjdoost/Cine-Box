package dev.aliakbar.tmdbunofficial.network

import retrofit2.http.GET

interface TMDBApiService
{
    @GET("configuration")
    suspend fun getConfiguration(): NetworkConfiguration
}