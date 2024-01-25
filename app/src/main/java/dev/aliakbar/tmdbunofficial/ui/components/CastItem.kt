package dev.aliakbar.tmdbunofficial.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

// Use for both cast and crew list item
@Composable
fun CastItem(
    id: Int,
    name: String,
    role: String,
    pictureUrl: String,
    onNavigateToPerson: (Int) -> Unit,
    modifier: Modifier = Modifier)
{
    ElevatedCard(
        onClick = {onNavigateToPerson(id)},
        modifier = Modifier.width(150.dp),
    )
    {

        Image(url = pictureUrl, modifier = Modifier.size(width = 150.dp, height = 225.dp))

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
