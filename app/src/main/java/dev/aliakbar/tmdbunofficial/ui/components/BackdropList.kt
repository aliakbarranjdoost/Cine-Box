package dev.aliakbar.tmdbunofficial.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Image

@Composable
fun BackdropList(
    backdrops: List<Image>,
    onPosterClick: (Image) -> Unit,
    contentPadding: PaddingValues = PaddingValues(horizontal = dimensionResource(id = R.dimen.padding_large)),
    modifier: Modifier = Modifier
)
{
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_between_list_item)),
        state = rememberLazyListState(),
        contentPadding = contentPadding,
        modifier = modifier
    )
    {
        items(items = backdrops)
        { backdrop ->
            BackdropItem(backdrop = backdrop, onPosterClick)
        }
    }
}

@Composable
fun BackdropItem(
    backdrop: Image,
    onPosterClick: (Image) -> Unit,
    modifier: Modifier = Modifier
)
{
    Card(
        modifier = modifier.size(width = 300.dp, height = 170.dp),
        onClick = { onPosterClick(backdrop) }
    )
    {
        Image(url = backdrop.fileUrl, modifier = Modifier.size(width = 300.dp, height = 170.dp))
    }
}
