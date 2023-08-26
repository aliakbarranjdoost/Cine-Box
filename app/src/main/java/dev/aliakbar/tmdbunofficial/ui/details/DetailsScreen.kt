package dev.aliakbar.tmdbunofficial.ui.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DetailsScreen(id: Int = 0)
{
    Text(text = "Movie Details $id")
}