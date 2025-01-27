package dev.aliakbar.tmdbunofficial.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.util.OVERVIEW_PREVIEW_MAX_LINE

@Composable
fun MainMovieDetailsRow(
    voteAverage: Float,
    runtime: Int,
    releaseDate: String,
    modifier: Modifier = Modifier
)
{
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    )
    {
        if (voteAverage > 0)
        {
            Text(
                text = "%.${1}f".format(voteAverage),
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = stringResource(R.string._10),
                fontWeight = FontWeight.Normal,
            )

            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color.Yellow,
            )

            Text(text = stringResource(R.string.details_divider))
        }

        if (runtime > 0)
        {
            val hour = runtime / 60
            val minute = runtime % 60

            val stringBuilder = StringBuilder()

            if (hour != 0)
            {
                stringBuilder.append(hour)
                stringBuilder.append(stringResource(R.string.hour))
            }
            if (minute != 0)
            {
                stringBuilder.append(minute)
                stringBuilder.append(stringResource(R.string.minute))
            }

            Text(
                text = stringBuilder.toString(),
                fontWeight = FontWeight.Medium
            )

            Text(text = stringResource(id = R.string.details_divider))
        }

        Text(
            text = releaseDate.substring(0..3),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun MainTvDetailsRow(
    voteAverage: Float,
    seasonNumber: Int,
    releaseDate: String,
    modifier: Modifier = Modifier
)
{
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    )
    {
        if (voteAverage > 0)
        {
            Text(
                text = "%.${1}f".format(voteAverage),
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = stringResource(id = R.string._10),
                fontWeight = FontWeight.Normal,
            )

            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color.Yellow,
            )

            Text(text = stringResource(id = R.string.details_divider))
        }

        Text(
            text = stringResource(id = R.string.total_seasons, seasonNumber),
            fontWeight = FontWeight.Medium
        )

        Text(text = stringResource(id = R.string.details_divider))

        Text(
            text = releaseDate.substring(0..3),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun TaglineText(
    tagline: String,
    modifier: Modifier = Modifier
)
{
    Text(
        text = tagline,
        maxLines = OVERVIEW_PREVIEW_MAX_LINE,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier
    )
}

@Composable
fun ShowMoreDetailsButton(
    showMore: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
)
{
    TextButton(
        onClick = { onClick() },
        modifier = modifier,
    )
    {
        Text(
            text =
            if (showMore) stringResource(R.string.less_details)
            else stringResource(R.string.more_details)
        )
    }
}

@Composable
fun SubDetailsRow(
    overview: String,
    homepage: String,
    modifier: Modifier = Modifier
)
{
    Column(modifier = modifier)
    {
        Text(text = overview, modifier = Modifier.padding(bottom = 8.dp))

        if (homepage.isNotEmpty())
        {
            val uriHandler = LocalUriHandler.current

            DetailsHeader(header = stringResource(R.string.home_page))

            TextButton(onClick = { uriHandler.openUri(homepage) }) {
                Text(text = homepage)
            }
        }
    }
}

@Composable
fun DetailsHeader(
    header: String,
    modifier: Modifier = Modifier
)
{
    Text(
        text = header,
        style = MaterialTheme.typography.titleMedium,
    )
}