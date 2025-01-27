package dev.aliakbar.tmdbunofficial.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aliakbar.tmdbunofficial.data.ThemeOptions
import dev.aliakbar.tmdbunofficial.data.source.datastore.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = SettingViewModel:: class.java.simpleName

@HiltViewModel
class SettingViewModel @Inject constructor(
    val repository: UserPreferencesRepository
): ViewModel()
{
    val settingsUiState: StateFlow<SettingsUiState> =
        repository.userPreferencesFlow
            .map { userData ->
                SettingsUiState(
                    settings = UserEditableSettings(
                        useDynamicColor = userData.dynamicTheme,
                        theme = userData.theme,
                    ),
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SettingsUiState(settings = UserEditableSettings()),
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
}

data class UserEditableSettings(
    val useDynamicColor: Boolean = false,
    val theme: ThemeOptions = ThemeOptions.SYSTEM_DEFAULT,
)

data class SettingsUiState(val settings: UserEditableSettings)