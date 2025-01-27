package dev.aliakbar.tmdbunofficial.ui.setting

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.ThemeOptions

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel()
)
{
    val uiState by viewModel.settingsUiState.collectAsStateWithLifecycle()

    Column(Modifier.padding(dimensionResource(id = R.dimen.padding_large)))
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        {
            SettingHeader(title = stringResource(R.string.use_dynamic_color))
            DynamicThemeSettingList(
                uiState.settings.useDynamicColor,
                onClick = {
                    viewModel.enableDynamicTheme(it)
                })
        }
        SettingHeader(title = stringResource(R.string.dark_mode_preference))
        DarkThemeSettingList(
            themeOptions = uiState.settings.theme,
            onClick = { viewModel.changeTheme(it) }
        )
    }
}

@Composable
fun SettingHeader(title: String, modifier: Modifier = Modifier)
{
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun DynamicThemeSettingList(
    useDynamicColor: Boolean,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
)
{
    Column(Modifier.selectableGroup()) {

        OptionItem(
            description = stringResource(R.string.yes),
            selected = useDynamicColor,
            onClick = { onClick(true) }
        )
        OptionItem(
            description = stringResource(R.string.no),
            selected = !useDynamicColor,
            onClick = { onClick(false) }
        )
    }
}

@Composable
fun DarkThemeSettingList(
    themeOptions: ThemeOptions,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
)
{
    Column(Modifier.selectableGroup())
    {
        OptionItem(
            description = stringResource(R.string.system_default),
            selected = themeOptions == ThemeOptions.SYSTEM_DEFAULT,
            onClick = { onClick(0) }
        )
        OptionItem(
            description = stringResource(R.string.light),
            selected = themeOptions == ThemeOptions.LIGHT,
            onClick = { onClick(1) }
        )
        OptionItem(
            description = stringResource(R.string.dark),
            selected = themeOptions == ThemeOptions.DARK,
            onClick = { onClick(2) }
        )
    }
}

@Composable
fun OptionItem(
    description: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
)
{
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp)
            .clickable { onClick() }
    )
    {
        RadioButton(
            selected = selected,
            onClick = null,
            modifier = Modifier.semantics { contentDescription = "Localized Description" }
        )
        Text(
            text = description,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}