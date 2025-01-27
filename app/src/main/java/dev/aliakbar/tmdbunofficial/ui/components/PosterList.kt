package dev.aliakbar.tmdbunofficial.ui.components

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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Image

@Composable
fun PosterList(
    posters: List<Image>,
    onPosterClick: (Image) -> Unit,
    modifier: Modifier = Modifier
)
{
        val scrollState = rememberLazyListState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_between_list_item)),
        state = scrollState,
        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_from_carousel))
    )
    {
        items(items = posters)
        { poster ->
            PosterItem(poster = poster, onPosterClick)
        }
    }
}

@Composable
fun PosterItem(
    poster: Image,
    onPosterClick: (Image) -> Unit,
    modifier: Modifier = Modifier
)
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
