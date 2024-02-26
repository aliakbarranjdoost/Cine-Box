package dev.aliakbar.tmdbunofficial.ui.components

import Carousel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.aliakbar.tmdbunofficial.data.Image

@Composable
fun BackdropList(backdrops: List<Image>, onPosterClick: (Image) -> Unit, modifier: Modifier = Modifier)
{
    val scrollState = rememberLazyListState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        state = scrollState,
        modifier = Modifier.padding(bottom = 1.dp)
    )
    {
        items(items = backdrops)
        { backdrop ->
            BackdropItem(backdrop = backdrop, onPosterClick)
        }
    }

    Carousel(state = scrollState, modifier = Modifier.fillMaxWidth())
}

@Composable
fun BackdropItem(backdrop: Image, onPosterClick: (Image) -> Unit)
{
    Card(
        modifier = Modifier.size(width = 300.dp, height = 170.dp),
        onClick = { onPosterClick(backdrop) }
    )
    {
        Image(url = backdrop.fileUrl, modifier = Modifier.size(width = 300.dp, height = 170.dp))
    }
}
