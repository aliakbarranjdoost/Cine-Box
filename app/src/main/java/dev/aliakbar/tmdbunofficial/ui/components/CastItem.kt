package dev.aliakbar.tmdbunofficial.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Cast

// Use for both cast and crew list item
@Composable
fun CastItem(name: String,role: String, pictureUrl: String, onCastClick: () -> Unit, modifier: Modifier = Modifier)
{
    Card(modifier = Modifier.width(150.dp), onClick = onCastClick)
    {
        AsyncImage(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .data(pictureUrl)
                .build(),
            placeholder = painterResource(id = R.drawable.profile_test),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = Modifier.size(width = 150.dp, height = 225.dp)
        )

        Text(
            text = name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = role,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        )
    }
}
