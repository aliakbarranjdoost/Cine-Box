package dev.aliakbar.tmdbunofficial.ui.person

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.aliakbar.tmdbunofficial.data.PersonDetails
import dev.aliakbar.tmdbunofficial.ui.components.CircularIndicator
import dev.aliakbar.tmdbunofficial.ui.components.Image
import dev.aliakbar.tmdbunofficial.ui.components.TopBar

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

    Scaffold(
        topBar = {
            TopBar(
                title = person.name,
                onNavigateBack = onNavigateBack,
            )
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)

        ) {
            Image(
                url = person.profileUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(CircleShape)
            )
        }
    }
}