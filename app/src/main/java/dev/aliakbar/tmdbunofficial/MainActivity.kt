package dev.aliakbar.tmdbunofficial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview()
{

}