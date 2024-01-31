package dev.aliakbar.tmdbunofficial.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import dev.aliakbar.tmdbunofficial.util.AUTHORIZATION_HEADER
import dev.aliakbar.tmdbunofficial.util.BASE_URL
import dev.aliakbar.tmdbunofficial.util.BEARER_TOKEN
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule
{
    @Provides
    fun provideJsonConfiguration() = Json()
    {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient
    {
        return OkHttpClient.Builder().addInterceptor()
        { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader(AUTHORIZATION_HEADER, BEARER_TOKEN)
                .build()
            chain.proceed(newRequest)
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttp: OkHttpClient, jsonConfig: Json): Retrofit
    {
        return Retrofit.Builder()
            .addConverterFactory(jsonConfig.asConverterFactory("application/json".toMediaType()))
            .client(okHttp)
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): TMDBApiService
    {
        return retrofit.create(TMDBApiService::class.java)
    }
}