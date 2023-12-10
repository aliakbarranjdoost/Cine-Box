package dev.aliakbar.tmdbunofficial.util

import androidx.compose.ui.graphics.Color

fun Float.toDegree(): Float
{
    return this * 12
}

fun Float.convertDegreeToHsvColor(): Color
{
    return Color.hsv(toDegree(),1F,1F)
}