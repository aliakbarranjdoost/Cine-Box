package dev.aliakbar.tmdbunofficial

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.aliakbar.tmdbunofficial.data.BookmarkRepository
import dev.aliakbar.tmdbunofficial.data.ConfigurationRepository
import dev.aliakbar.tmdbunofficial.data.DetailsRepository
import dev.aliakbar.tmdbunofficial.data.EpisodeRepository
import dev.aliakbar.tmdbunofficial.data.GenreTopRepository
import dev.aliakbar.tmdbunofficial.data.HomeRepository
import dev.aliakbar.tmdbunofficial.data.PersonRepository
import dev.aliakbar.tmdbunofficial.data.SearchRepository
import dev.aliakbar.tmdbunofficial.data.SeasonDetailsRepository
import dev.aliakbar.tmdbunofficial.data.TopRepository
import dev.aliakbar.tmdbunofficial.data.source.datastore.UserPreferencesRepository
import dev.aliakbar.tmdbunofficial.data.source.local.TmdbDatabase
import dev.aliakbar.tmdbunofficial.data.source.network.TMDBApiService
import dev.aliakbar.tmdbunofficial.util.AUTHORIZATION_HEADER
import dev.aliakbar.tmdbunofficial.util.BASE_URL
import dev.aliakbar.tmdbunofficial.util.BEARER_TOKEN
import dev.aliakbar.tmdbunofficial.util.SETTING_KEY
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
    val seasonDetailsRepository: SeasonDetailsRepository
    val episodeRepository: EpisodeRepository
    val genreTopRepository: GenreTopRepository
    val personRepository: PersonRepository
    val userPersonRepository: UserPreferencesRepository
}


class DefaultAppContainer(context: Context): AppContainer
{
    private val okhttp = OkHttpClient.Builder().addInterceptor()
    {   chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader(AUTHORIZATION_HEADER, BEARER_TOKEN)
            .build()
        chain.proceed(newRequest)
    }

    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(okhttp.build())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: TMDBApiService by lazy {
        retrofit.create(TMDBApiService::class.java)
    }

    private val roomDatabase: TmdbDatabase = TmdbDatabase.getDatabase(context)

    override val configurationRepository: ConfigurationRepository by lazy()
    {
        ConfigurationRepository(retrofitService, roomDatabase.configurationDao(), roomDatabase.bookmarkDao())
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
    override val seasonDetailsRepository: SeasonDetailsRepository by lazy()
    {
        SeasonDetailsRepository(retrofitService,roomDatabase)
    }
    override val episodeRepository: EpisodeRepository by lazy()
    {
        EpisodeRepository(
            retrofitService,
            roomDatabase
        )
    }
    override val genreTopRepository: GenreTopRepository by lazy()
    {
        GenreTopRepository(
            retrofitService,
            roomDatabase
        )
    }
    override val personRepository: PersonRepository by lazy()
    {
        PersonRepository(
            retrofitService,
            roomDatabase
        )
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTING_KEY)

    override val userPersonRepository: UserPreferencesRepository by lazy()
    {
        UserPreferencesRepository(
            context.dataStore
        )
    }
}