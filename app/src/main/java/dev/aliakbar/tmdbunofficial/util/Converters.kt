package dev.aliakbar.tmdbunofficial.util

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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