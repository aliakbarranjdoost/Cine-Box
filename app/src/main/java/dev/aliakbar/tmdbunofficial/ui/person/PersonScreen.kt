package dev.aliakbar.tmdbunofficial.ui.person

import Carousel
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.aliakbar.tmdbunofficial.R
import dev.aliakbar.tmdbunofficial.data.PersonDetails
import dev.aliakbar.tmdbunofficial.data.PersonMovieAsCast
import dev.aliakbar.tmdbunofficial.data.PersonMovieAsCrew
import dev.aliakbar.tmdbunofficial.data.PersonTvAsCast
import dev.aliakbar.tmdbunofficial.data.PersonTvAsCrew
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicator
import dev.aliakbar.tmdbunofficial.ui.components.DetailsHeader
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.components.ListTitleText
import dev.aliakbar.tmdbunofficial.ui.components.ShowMoreDetailsButton
import dev.aliakbar.tmdbunofficial.ui.components.TopBar
import dev.aliakbar.tmdbunofficial.util.OVERVIEW_PREVIEW_MAX_LINE

// TODO: make backdrop path optional
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
        is PersonUiState.Error   -> Text(text = "Error")
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
            else
            {
                Column(modifier = Modifier.padding(16.dp))
                {
                    Text(text = person.biography, modifier = Modifier.padding(bottom = 16.dp))
                    DetailsHeader(header = stringResource(R.string.known_for))
                    Text(text = person.knownForDepartment)
                    DetailsHeader(header = stringResource(R.string.birthday))
                    Text(text = person.birthday)
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

            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp))
            {
                if (person.asMovieCast.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.movies_as_cast,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                    CreditList(credits = person.asMovieCast, onNavigate = onNavigateToMovie)
                }

                if (person.asTvCast.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.tvs_as_cast,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                    CreditList(credits = person.asTvCast, onNavigate = onNavigateToTv)
                }

                if (person.asMovieCrew.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.movies_as_crew,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                    CreditList(credits = person.asMovieCrew, onNavigate = onNavigateToMovie)
                }

                if (person.asTvCrew.isNotEmpty())
                {
                    ListTitleText(
                        title = R.string.tvs_as_crew,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                    CreditList(credits = person.asTvCrew, onNavigate = onNavigateToTv)
                }
            }
        }
    }
}

@Composable
fun <T> CreditList(
    credits : List<T>,
    onNavigate: (Int) -> Unit,
    modifier: Modifier = Modifier)
{
    val scrollState = rememberLazyListState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        state = scrollState,
        modifier = Modifier.padding(bottom = 2.dp)
    )
    {
        items(items = credits)
        { credit ->
            when(credit)
            {
                is PersonMovieAsCast ->
                    CreditItem(
                        id = credit.id,
                        title = credit.title,
                        poster = credit.posterUrl,
                        personRole = credit.character,
                        onNavigate = onNavigate
                    )
                is PersonTvAsCast ->
                    CreditItem(
                        id = credit.id,
                        title = credit.name,
                        poster = credit.posterUrl,
                        personRole = credit.character,
                        onNavigate = onNavigate
                    )
                is PersonMovieAsCrew ->
                    CreditItem(
                        id = credit.id,
                        title = credit.title,
                        poster = credit.posterUrl,
                        personRole = credit.job,
                        onNavigate = onNavigate
                    )
                is PersonTvAsCrew ->
                    CreditItem(
                        id = credit.id,
                        title = credit.name,
                        poster = credit.posterUrl,
                        personRole = credit.job,
                        onNavigate = onNavigate
                    )
            }
        }
    }

    Carousel(state = scrollState, modifier = Modifier.fillMaxWidth())
}

@Composable
fun CreditItem(
    id: Int,
    title: String,
    poster: String,
    personRole: String,
    onNavigate: (Int) -> Unit
)
{
    ElevatedCard(
        onClick = { onNavigate(id) },
        modifier = Modifier.width(150.dp)
    )
    {
        Image(url = poster, modifier = Modifier.size(width = 150.dp, height = 225.dp))

        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = personRole,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(8.dp)
        )
    }
}
