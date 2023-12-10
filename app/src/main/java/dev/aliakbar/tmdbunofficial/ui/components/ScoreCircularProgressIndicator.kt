package dev.aliakbar.tmdbunofficial.ui.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.aliakbar.tmdbunofficial.util.convertDegreeToHsvColor

@Composable
fun ScoreCircularProgressIndicator(
    score: Float,
    modifier: Modifier = Modifier,
)
{
    CircularProgressIndicator(
        progress = { score / 10 },
        modifier = Modifier,
        color = score.convertDegreeToHsvColor()
    )
}