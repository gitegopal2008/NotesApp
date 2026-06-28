package com.notesapp.data.local

import androidx.room.TypeConverter
import org.json.JSONArray

class Converters {

    @TypeConverter
    fun fromTagsJson(value: String): List<String> {
        if (value.isBlank()) return emptyList()
        val jsonArray = JSONArray(value)
        return (0 until jsonArray.length()).map { jsonArray.getString(it) }
    }

    @TypeConverter
    fun toTagsJson(tags: List<String>): String {
        return JSONArray(tags).toString()
    }
}
