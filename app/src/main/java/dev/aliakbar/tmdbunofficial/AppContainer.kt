package dev.aliakbar.tmdbunofficial

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.aliakbar.tmdbunofficial.data.BookmarkRepository
import dev.aliakbar.tmdbunofficial.data.ConfigurationRepository
import dev.aliakbar.tmdbunofficial.data.DetailsRepository
import dev.aliakbar.tmdbunofficial.data.HomeRepository
import dev.aliakbar.tmdbunofficial.data.SearchRepository
import dev.aliakbar.tmdbunofficial.data.TopRepository
import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AppContainer
{
    val configurationRepository: ConfigurationRepository
    val homeRepository: HomeRepository
    val detailsRepository: DetailsRepository
    val bookmarkRepository: BookmarkRepository
    val topRepository: TopRepository
    val searchRepository: SearchRepository
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

    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
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

    override val homeRepository: HomeRepository by lazy()
    {
        HomeRepository(
            retrofitService,
            roomDatabase
        )
    }
    override val detailsRepository: DetailsRepository by lazy()
    {
        DetailsRepository(
            retrofitService,
            roomDatabase
        )
    }
    override val bookmarkRepository: BookmarkRepository by lazy()
    {
        BookmarkRepository(
            retrofitService,
            roomDatabase
        )
    }
    override val topRepository: TopRepository by lazy()
    {
        TopRepository(
            retrofitService,
            roomDatabase
        )
    }
    override val searchRepository: SearchRepository by lazy()
    {
        SearchRepository(
            retrofitService,
            roomDatabase
        )
    }
}