package dev.aliakbar.tmdbunofficial.util

fun Float.toPercent(): Int
{
    return toInt() * 10
}

fun Float.toDegree(): Float
{
    return (toInt() * 12).toFloat()
}