package dev.aliakbar.tmdbunofficial.ui.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import dev.aliakbar.tmdbunofficial.R

@Composable
fun SettingScreen()
{
    Column(Modifier.padding(16.dp))
    {
        SettingHeader(title = "Use Dynamic Color")
        DynamicThemeSettingList()
        SettingHeader(title = "Dark Mode Preference")
        DarkThemeSettingList()
    }
}

@Composable
fun SettingHeader(title: String)
{
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun DynamicThemeSettingList()
{
    var state by remember { mutableStateOf(true) }

    Column(Modifier.selectableGroup()) {

        OptionItem(
            description = stringResource(R.string.yes),
            selected = state,
            onClick = { state = !state }
        )
        OptionItem(
            description = stringResource(R.string.no),
            selected = !state,
            onClick = { state = !state }
        )
    }
}

@Composable
fun DarkThemeSettingList()
{
    var state by remember { mutableIntStateOf(0) }

    Column(Modifier.selectableGroup()) {

        OptionItem(
            description = stringResource(R.string.system_default),
            selected = state == 0,
            onClick = { state = 0 }
        )
        OptionItem(
            description = stringResource(R.string.light),
            selected = state == 1,
            onClick = { state = 1 }
        )
        OptionItem(
            description = stringResource(R.string.dark),
            selected = state == 2,
            onClick = { state = 2 }
        )
    }
}

@Composable
fun OptionItem(description: String, selected: Boolean, onClick: () -> Unit)
{
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() })
    {
        RadioButton(
            selected = selected,
            onClick = { },
            modifier = Modifier.semantics { contentDescription = "Localized Description" }
        )
        Text(
            text = description,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}