package dev.aliakbar.tmdbunofficial.util

import androidx.room.TypeConverter
import dev.aliakbar.tmdbunofficial.data.Episode
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

class Converters
{
    @TypeConverter
    fun fromStringListToJson(items: List<String>) : String
    {
        return Json.encodeToString(items)
    }

    @TypeConverter
    fun jsonToStringList(items: String) : List<String>
    {
        return Json.decodeFromString(items)
    }
}

fun Episode.toJsonString(): String
{
    return Json.encodeToString(this.toString())
}

fun String.toEpisode(): Episode
{
    return Json.decodeFromString<Episode>(this)
}
