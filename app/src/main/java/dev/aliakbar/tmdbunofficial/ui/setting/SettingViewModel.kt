package dev.aliakbar.tmdbunofficial.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.aliakbar.tmdbunofficial.TmdbUnofficialApplication
import dev.aliakbar.tmdbunofficial.ui.season.SeasonViewModel

private val TAG = SettingViewModel:: class.java.simpleName

class SettingViewModel(): ViewModel()
{

    companion object
    {
        val factory: ViewModelProvider.Factory = viewModelFactory()
        {
            initializer()
            {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TmdbUnofficialApplication)
                //val repository = application.container.seasonDetailsRepository
                //val savedStateHandle = this.createSavedStateHandle()
                SettingViewModel()
            }
        }
    }
}

data class UserEditableSettings(
    val useDynamicColor: Boolean,
    //val darkThemeConfig: DarkThemeConfig,
)

sealed interface SettingsUiState {
    object Loading : SettingsUiState
    data class Success(val settings: UserEditableSettings) : SettingsUiState
}