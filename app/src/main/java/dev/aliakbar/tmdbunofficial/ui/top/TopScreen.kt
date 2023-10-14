package dev.aliakbar.tmdbunofficial.ui.top

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Text
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import dev.aliakbar.tmdbunofficial.R

@Composable
fun TopScreen(
    navController: NavHostController,
    viewModel: TopViewModel = viewModel(factory = TopViewModel.factory),
    modifier: Modifier = Modifier
)
{
    var tabState by remember { mutableIntStateOf(0) }
    val titles = stringArrayResource(id = R.array.content_type_option)

    Column()
    {
        PrimaryTabRow(selectedTabIndex = tabState)
        {
            titles.forEachIndexed()
            { index, title ->
                Tab(
                    text = { Text(text = title) },
                    onClick = { tabState = index },
                    selected = (index == tabState)
                )
            }
        }
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Fancy tab ${tabState + 1} selected",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun TopScreen()
{

}