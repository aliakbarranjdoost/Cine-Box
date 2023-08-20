package dev.aliakbar.tmdbunofficial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.aliakbar.tmdbunofficial.ui.home.HomeScreen
import dev.aliakbar.tmdbunofficial.ui.main.MainUiState
import dev.aliakbar.tmdbunofficial.ui.main.MainViewModel
import dev.aliakbar.tmdbunofficial.ui.main.TmdbApp
import dev.aliakbar.tmdbunofficial.ui.theme.TMDBUnofficialTheme

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent()
        {
            TMDBUnofficialTheme(darkTheme = false, dynamicColor = false)
            {
                TmdbApp()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier)
{
    val mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory)
    var mainUiState = mainViewModel.mainUiState

    when (mainUiState)
    {
        is MainUiState.Loading              ->
            Text(
                text = "Loading",
                modifier = modifier
            )

        is MainUiState.ConfigurationSuccess ->
            Text(
                text = "${mainUiState.imageConfiguration}",
                modifier = modifier
            )

        is MainUiState.Error                ->
            Text(
                text = "Error",
                modifier = modifier
            )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview()
{
    TMDBUnofficialTheme {
        Greeting("Android")
    }
}