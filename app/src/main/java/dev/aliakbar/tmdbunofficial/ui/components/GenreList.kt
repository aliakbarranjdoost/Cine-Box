package dev.aliakbar.tmdbunofficial.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Genre

@Composable
fun GenreList(
    genres: List<Genre>,
    type: Boolean,
    onNavigateToGenreTop: (Int, String, Boolean) -> Unit,
    contentPadding: PaddingValues = PaddingValues(horizontal = dimensionResource(id = R.dimen.padding_large)),
    modifier: Modifier = Modifier
)
{
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        contentPadding = contentPadding,
        modifier = modifier
    )
    {
        items(items = genres)
        { genre ->
            TextButton(onClick = { onNavigateToGenreTop(genre.id, genre.name, type) })
            {
                Text(text = genre.name)
            }
        }
    }
}
