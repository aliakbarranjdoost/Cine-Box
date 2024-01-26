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
import dev.aliakbar.tmdbunofficial.data.Trend

@Composable
fun RecommendationList(
    recommendations: List<Trend>,
    onNavigateToMovie: (Int) -> Unit,
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
                onNavigateToMovie = onNavigateToMovie
            )
        }
    }

    Carousel(state = scrollState, modifier = Modifier.fillMaxWidth())
}

@Composable
fun RecommendationItem(recommendation: Trend, onNavigateToMovie: (Int) -> Unit)
{
    Card(
        onClick = { onNavigateToMovie(recommendation.id) }
    )
    {
        Image(
            url = recommendation.poster,
            modifier = Modifier.size(width = 170.dp, height = 255.dp)
        )
    }
}