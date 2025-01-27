package dev.aliakbar.tmdbunofficial.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.Person

// TODO: Move to details file
@Composable
fun ListTitleText(
    @StringRes
    title: Int,
    modifier: Modifier = Modifier
)
{
    Text(
        text = stringResource(id = title),
        style = MaterialTheme.typography.titleMedium,
        fontSize = 20.sp,
        modifier = modifier,
    )
}

@Composable
fun PersonList(
    persons: List<Person>,
    onNavigateToPerson: (Int) -> Unit,
    contentPadding: PaddingValues = PaddingValues(horizontal = dimensionResource(id = R.dimen.padding_large)),
    modifier: Modifier = Modifier
)
{
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_between_list_item)),
        state = rememberLazyListState(),
        contentPadding = contentPadding,
        modifier = modifier,
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
        modifier = modifier.width(150.dp),
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
