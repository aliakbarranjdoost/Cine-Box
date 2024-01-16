package dev.aliakbar.tmdbunofficial.ui.person

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.aliakbar.tmdbunofficial.data.PersonDetails
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicator
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.components.TopBar
import dev.aliakbar.tmdbunofficial.ui.movie.DetailsHeader
import dev.aliakbar.tmdbunofficial.ui.movie.ListHeader
import dev.aliakbar.tmdbunofficial.ui.movie.OVERVIEW_PREVIEW_MAX_LINE

@Composable
fun PersonScreen(
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToTv: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PersonViewModel = viewModel(factory = PersonViewModel.factory)
)
{
    val uiState = viewModel.personUiState

    when (uiState)
    {
        is PersonUiState.Loading -> CircularIndicator()
        is PersonUiState.Error   -> Text(text = "Error")
        is PersonUiState.Success ->
        {
            PersonScreen(
                person = uiState.person,
                onNavigateToMovie = {},
                onNavigateToTv = {},
                onNavigateBack = { /*TODO*/ })
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
            horizontalAlignment = Alignment.CenterHorizontally,
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
                    //.align(Alignment.CenterHorizontally)
            )

            if (!showDetails)
            {
                Text(
                    text = person.biography,
                    maxLines = OVERVIEW_PREVIEW_MAX_LINE,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                )

                TextButton(
                    onClick = { showDetails = true },
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                )
                {
                    Text(text = "More Details")
                }
            }
            else
            {
                Column(modifier = Modifier.padding(16.dp))
                {
                    Text(text = person.biography)
                    DetailsHeader(header = "Known For")
                    Text(text = person.knownForDepartment)
                    DetailsHeader(header = "Birthday")
                    Text(text = person.birthday)
                    if (person.deathDay != null)
                    {
                        DetailsHeader(header = "Day of Death")
                        Text(text = person.deathDay)
                    }
                    DetailsHeader(header = "Place of Birth")
                    Text(text = person.placeOfBirth)
                    TextButton(
                        onClick = { showDetails = false },
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    )
                    {
                        Text(text = "less Details")
                    }
                }
            }

            ListHeader(header = "Movies as Cast")
            //RecommendationList
            ListHeader(header = "Tvs as Cast")
            ListHeader(header = "Movies as Crew")
            ListHeader(header = "Tvs as Crew")

        }
    }
}