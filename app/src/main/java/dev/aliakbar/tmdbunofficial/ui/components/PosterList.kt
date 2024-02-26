package dev.aliakbar.tmdbunofficial.ui.components

import Carousel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.aliakbar.tmdbunofficial.data.Image

@Composable
fun PosterList(posters: List<Image>, onPosterClick: (Image) -> Unit, modifier: Modifier = Modifier)
{
        val scrollState = rememberLazyListState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        state = scrollState,
        modifier = Modifier.padding(bottom = 1.dp)
    )
    {
        items(items = posters)
        { poster ->
            PosterItem(poster = poster, onPosterClick)
        }
    }

    Carousel(state = scrollState, modifier = Modifier.fillMaxWidth())
}

@Composable
fun PosterItem(poster: Image, onPosterClick: (Image) -> Unit)
{
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(300.dp),
        onClick = { onPosterClick(poster) }
    )
    {
        Image(url = poster.fileUrl, modifier = Modifier
            .width(200.dp)
            .height(300.dp),)
    }
}
