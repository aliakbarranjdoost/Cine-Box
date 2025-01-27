package dev.aliakbar.tmdbunofficial.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import dev.aliakbar.tmdbunofficial.R

@Composable
fun EmptyScreen(@StringRes text: Int, modifier: Modifier = Modifier)
{
    Box(modifier = modifier.fillMaxSize().padding(dimensionResource(R.dimen.padding_large)))
    {
        Text(text = stringResource(text), modifier = Modifier.align(Alignment.Center))
    }
}