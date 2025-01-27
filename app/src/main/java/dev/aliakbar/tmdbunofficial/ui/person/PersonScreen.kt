package dev.aliakbar.tmdbunofficial.ui.person

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.PersonCredit
import dev.aliakbar.tmdbunofficial.data.PersonDetails
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicator
import dev.aliakbar.tmdbunofficial.ui.components.DetailsHeader
import dev.aliakbar.tmdbunofficial.ui.components.ErrorButton
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.components.ListTitleText
import dev.aliakbar.tmdbunofficial.ui.components.ShowMoreDetailsButton
import dev.aliakbar.tmdbunofficial.ui.components.TopBar
import dev.aliakbar.tmdbunofficial.util.OVERVIEW_PREVIEW_MAX_LINE

@Composable
fun PersonScreen(
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToTv: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PersonViewModel = hiltViewModel()
)
{
    when (val uiState = viewModel.personUiState)
    {
        is PersonUiState.Loading -> CircularIndicator()
        is PersonUiState.Error   -> ErrorButton {
            viewModel.getPerson()
        }
        is PersonUiState.Success ->
        {
            PersonScreen(
                person = uiState.person,
                onNavigateToMovie = { onNavigateToMovie(it) },
                onNavigateToTv = { onNavigateToTv(it) },
                onNavigateBack = { onNavigateBack() })
        }
    }
}

@Composable
fun PersonScreen(
    person: PersonDetails,
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToTv: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
)
{
    val scrollState = rememberScrollState()
    var showDetails by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                title = person.name,
                onNavigateBack = onNavigateBack,
            )
        },
        modifier = Modifier.fillMaxSize()
    )
    { innerPadding ->
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            Image(
                url = person.profileUrl,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )

            if (!showDetails)
            {
                Text(
                    text = person.biography,
                    maxLines = OVERVIEW_PREVIEW_MAX_LINE,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                )

                ShowMoreDetailsButton(
                    showMore = showDetails,
                    onClick = { showDetails = !showDetails },
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                )
            }
            AnimatedVisibility(visible = showDetails)
            {
                Column(modifier = Modifier.padding( horizontal = 16.dp))
                {
                    Text(text = person.biography, modifier = Modifier.padding(vertical = 16.dp))
                    DetailsHeader(header = stringResource(R.string.known_for))
                    Text(text = person.knownForDepartment)

                    if (person.birthday != null)
                    {
                        DetailsHeader(header = stringResource(R.string.birthday))
                        Text(text = person.birthday)
                    }

                    if (person.deathDay != null)
                    {
                        DetailsHeader(header = stringResource(R.string.day_of_death))
                        Text(text = person.deathDay)
                    }

                    if (person.placeOfBirth != null)
                    {
                        DetailsHeader(header = stringResource(R.string.place_of_birth))
                        Text(text = person.placeOfBirth)
                    }

                    ShowMoreDetailsButton(
                        showMore = showDetails,
                        onClick = { showDetails = !showDetails },
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }
            }

            val topPadding = dimensionResource(id = R.dimen.padding_large)
            val bottomPadding = dimensionResource(id = R.dimen.padding_medium)

            Column(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_large)))
            {
                if (person.asMovieCast.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.movies_as_cast,
                        modifier = Modifier.padding(top = topPadding, bottom = bottomPadding)
                    )
                    CreditList(credits = person.asMovieCast, onNavigate = onNavigateToMovie)
                }

                if (person.asTvCast.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.tvs_as_cast,
                        modifier = Modifier.padding(top = topPadding, bottom = bottomPadding)
                    )
                    CreditList(credits = person.asTvCast, onNavigate = onNavigateToTv)
                }

                if (person.asMovieCrew.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.movies_as_crew,
                        modifier = Modifier.padding(top = topPadding, bottom = bottomPadding)
                    )
                    CreditList(credits = person.asMovieCrew, onNavigate = onNavigateToMovie)
                }

                if (person.asTvCrew.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.tvs_as_crew,
                        modifier = Modifier.padding(top = topPadding, bottom = bottomPadding)
                    )
                    CreditList(credits = person.asTvCrew, onNavigate = onNavigateToTv)
                }
            }
        }
    }
}

@Composable
fun CreditList(
    credits : List<PersonCredit>,
    onNavigate: (Int) -> Unit,
    modifier: Modifier = Modifier
)
{
    val scrollState = rememberLazyListState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_between_list_item)),
        state = scrollState,
        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_from_carousel))
    )
    {
        items(items = credits)
        { credit ->

            CreditItem(
                personCredit = credit,
                onNavigate = onNavigate
            )
        }
    }
}

@Composable
fun CreditItem(
    personCredit: PersonCredit,
    onNavigate: (Int) -> Unit,
    modifier: Modifier = Modifier
)
{
    ElevatedCard(
        onClick = { onNavigate(personCredit.id) },
        modifier = Modifier.width(150.dp)
    )
    {
        Image(url = personCredit.poster, modifier = Modifier.size(width = 150.dp, height = 225.dp))

        Text(
            text = personCredit.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        )
        Text(
            text = personCredit.role,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        )
    }
}
