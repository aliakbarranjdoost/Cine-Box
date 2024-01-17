package dev.aliakbar.tmdbunofficial.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.aliakbar.tmdbunofficial.TmdbUnofficialApplication
import dev.aliakbar.tmdbunofficial.data.source.datastore.ThemeOptions
import dev.aliakbar.tmdbunofficial.data.source.datastore.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private val TAG = SettingViewModel:: class.java.simpleName

class SettingViewModel(
    private val repository: UserPreferencesRepository
): ViewModel()
{
    val settingsUiState: StateFlow<SettingsUiState> =
        repository.userPreferencesFlow
            .map { userData ->
                SettingsUiState.Success(
                    settings = UserEditableSettings(
                        useDynamicColor = userData.dynamicTheme,
                        theme = userData.theme,
                    ),
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SettingsUiState.Loading,
            )

    fun enableDynamicTheme(enable: Boolean)
    {
        viewModelScope.launch { repository.activeDynamicColor(enable) }
    }

    fun changeTheme(themeIndex: Int)
    {
        val selectedTheme = when(themeIndex)
        {
            1 -> ThemeOptions.LIGHT
            2 -> ThemeOptions.DARK
            else -> ThemeOptions.SYSTEM_DEFAULT
        }

        viewModelScope.launch { repository.changeTheme(selectedTheme) }
    }

    companion object
    {
        val factory: ViewModelProvider.Factory = viewModelFactory()
        {
            initializer()
            {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TmdbUnofficialApplication)
                val repository = application.container.userPersonRepository
                SettingViewModel(repository)
            }
        }
    }
}

data class UserEditableSettings(
    val useDynamicColor: Boolean,
    val theme: ThemeOptions,
)

sealed interface SettingsUiState {
    object Loading : SettingsUiState
    data class Success(val settings: UserEditableSettings) : SettingsUiState
}