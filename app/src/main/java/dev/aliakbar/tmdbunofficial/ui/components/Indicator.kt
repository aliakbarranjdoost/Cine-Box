package dev.aliakbar.tmdbunofficial.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IndicatorList(itemCount: Int, selectedItem: Int, modifier: Modifier = Modifier)
{
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth(0.5f).padding(bottom = 8.dp))
    {
        for( i in 0..<itemCount)
        {
            IndicatorItem(isSelected = (i == selectedItem))
        }
    }
}

@Composable
fun IndicatorItem(isSelected: Boolean)
{
    OutlinedButton(
        onClick = {  },
        border = BorderStroke(1.dp, Color.Black),
        shape = CircleShape,
        modifier = Modifier.size(10.dp).padding(1.dp),
        colors = if (isSelected) ButtonDefaults.outlinedButtonColors(containerColor = Color.Red) else ButtonDefaults.outlinedButtonColors()
    )
    {

    }
}

@Preview
@Composable
fun PreviewIndicatorItem()
{
    IndicatorItem(isSelected = true)
}

@Preview
@Composable
fun PreviewIndicatorList()
{
    IndicatorList(itemCount = 7, selectedItem = 3)
}