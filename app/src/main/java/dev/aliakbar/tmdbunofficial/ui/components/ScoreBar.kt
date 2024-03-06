package dev.aliakbar.tmdbunofficial.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.aliakbar.tmdbunofficial.util.convertDegreeToHsvColor
import kotlin.math.roundToInt

@Composable
fun ScoreBar(
    score: Float,
    modifier: Modifier = Modifier
)
{
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.padding(4.dp)
    )
    {
        ScoreCircularText(score = score)

        ScoreCircularProgressIndicator(score = score)
    }
}

@Composable
fun ScoreCircularText(
    score: Float,
    modifier: Modifier = Modifier,
)
{
    Text(
        text = (score * 10).roundToInt().toString() + "%",
        color = Color.Black,
        modifier = modifier.clip(CircleShape),
    )
}

@Composable
fun ScoreCircularProgressIndicator(
    score: Float,
    modifier: Modifier = Modifier,
)
{
    CircularProgressIndicator(
        progress = { score / 10 },
        modifier = modifier,
        color = score.convertDegreeToHsvColor()
    )
}