package dev.aliakbar.tmdbunofficial.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Rank(rank : Int, modifier: Modifier = Modifier)
{
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    )
    {
        Text(
            text = rank.toString(),
            maxLines = 1,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(4.dp),
            textAlign = TextAlign.Center,
        )
    }

}