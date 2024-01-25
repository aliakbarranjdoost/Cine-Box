package dev.aliakbar.tmdbunofficial.ui.components

import Carousel
import androidx.annotation.StringRes
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.aliakbar.tmdbunofficial.data.Person

@Composable
fun ListTitleText(@StringRes title: Int, modifier: Modifier = Modifier)
{
    Text(
        text = stringResource(id = title),
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier,
    )
}

@Composable
fun PersonList(
    persons: List<Person>,
    onNavigateToPerson: (Int) -> Unit,
    modifier: Modifier = Modifier
)
{
    val scrollState = rememberLazyListState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = scrollState,
        modifier = Modifier.padding(bottom = 2.dp),
    )
    {
        items(items = persons)
        { person ->
            PersonItem(
                id = person.id,
                name = person.name,
                role = person.role,
                pictureUrl = person.profileUrl,
                onNavigateToPerson = onNavigateToPerson
            )
        }
    }

    Carousel(state = scrollState, modifier = Modifier.fillMaxWidth())
}

@Composable
fun PersonItem(
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
