package dev.aliakbar.tmdbunofficial

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.aliakbar.tmdbunofficial.data.ConfigurationRepository
import dev.aliakbar.tmdbunofficial.data.TrendingRepository
import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AppContainer
{
    val configurationRepository: ConfigurationRepository
    val trendingRepository: TrendingRepository
}

class DefaultAppContainer(private val context: Context): AppContainer
{
    private val baseUrl = "https://api.themoviedb.org/3/"
    private val bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhNzkwYjcwMzRkOTFhODU0YmE5MmUxOTlkMWQ2MTk3MiIsInN1YiI6IjYzMGYxMTg0MTI0MjVjMDA5ZDdkMjAzZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xPKQ-BTT_SZqBtJKyQ36VoDDpqCr_BAp-b_NjOOXvhc"

    private val okhttp = OkHttpClient.Builder().addInterceptor()
    {   chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $bearerToken")
            .build()
        chain.proceed(newRequest)
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .client(okhttp.build())
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: TMDBApiService by lazy {
        retrofit.create(TMDBApiService::class.java)
    }

    private val roomDatabase: TmdbDatabase = TmdbDatabase.getDatabase(context)

    override val configurationRepository: ConfigurationRepository by lazy()
    {
        ConfigurationRepository(
            retrofitService,
            roomDatabase.configurationDao()
        )
    }

    override val trendingRepository: TrendingRepository by lazy()
    {
        TrendingRepository(
            retrofitService,
            roomDatabase.configurationDao()
        )
    }
}