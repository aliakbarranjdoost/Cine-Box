package dev.aliakbar.tmdbunofficial.ui.components

import Carousel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.aliakbar.tmdbunofficial.data.Trend

@Composable
fun RecommendationList(
    recommendations: List<Trend>,
    onNavigateToRecommend: (Int) -> Unit,
    modifier: Modifier = Modifier
)
{
    val scrollState = rememberLazyListState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        state = scrollState,
        modifier = Modifier.padding(bottom = 1.dp)
    )
    {
        items(items = recommendations)
        { recommendation ->
            RecommendationItem(
                recommendation = recommendation,
                onNavigateToRecommend = onNavigateToRecommend
            )
        }
    }

    Carousel(state = scrollState, modifier = Modifier.fillMaxWidth())
}

@Composable
fun RecommendationItem(recommendation: Trend, onNavigateToRecommend: (Int) -> Unit)
{
    ElevatedCard(
        onClick = { onNavigateToRecommend(recommendation.id) },
        modifier = Modifier.width(170.dp)
    )
    {
        Image(
            url = recommendation.poster,
            modifier = Modifier.size(width = 170.dp, height = 255.dp)
        )

        Text(
            text = recommendation.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleSmall,

            modifier = Modifier.padding(8.dp)
        )
    }
}