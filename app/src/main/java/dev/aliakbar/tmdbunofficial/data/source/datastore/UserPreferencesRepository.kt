package dev.aliakbar.tmdbunofficial.data.source.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.aliakbar.tmdbunofficial.data.ThemeOptions
import dev.aliakbar.tmdbunofficial.util.DYNAMIC_THEME_KEY
import dev.aliakbar.tmdbunofficial.util.THEME_OPTION_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

data class UserPreferences(
    val dynamicTheme: Boolean,
    val theme: ThemeOptions
)

private var TAG = UserPreferencesRepository::class.java.simpleName

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
)
{
    object PreferencesKeys
    {
        val IS_DYNAMIC = booleanPreferencesKey(DYNAMIC_THEME_KEY)
        val THEME_OPTIONS = stringPreferencesKey(THEME_OPTION_KEY)
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException)
            {
                emit(emptyPreferences())
            }
            else
            {
                throw exception
            }
        }.map()
        { preferences ->
            mapUserPreferences(preferences)
        }

    suspend fun activeDynamicColor(active: Boolean)
    {
        dataStore.edit()
        { preferences ->
            preferences[PreferencesKeys.IS_DYNAMIC] = active
        }
    }

    suspend fun changeTheme(options: ThemeOptions)
    {
        dataStore.edit()
        { preferences ->
            preferences[PreferencesKeys.THEME_OPTIONS] = options.name
        }
    }

    suspend fun fetchInitialPreferences() =
        mapUserPreferences(dataStore.data.first().toPreferences())

    private fun mapUserPreferences(preferences: Preferences): UserPreferences
    {
        val isDynamic = preferences[PreferencesKeys.IS_DYNAMIC] ?: false
        val themeOption = ThemeOptions.valueOf(
            preferences[PreferencesKeys.THEME_OPTIONS] ?: ThemeOptions.SYSTEM_DEFAULT.name
        )
        return UserPreferences(isDynamic, themeOption)
    }
}