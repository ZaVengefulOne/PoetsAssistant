package com.example.composejoyride.data.parcers

import com.example.composejoyride.data.entitites.Poem
import org.json.JSONArray

data class ArsPoeticaEntryDto(
    val author: String?,
    val title: String?,
    val poemText: String,
    val accentuation: String?,
)

fun ArsPoeticaEntryDto.toEntity(index: Int) = Poem(
    id = index,
    author = author?.takeIf { it.isNotBlank() },
    title = title?.takeIf { it.isNotBlank() },
    poemText = poemText,
    accentuationMarkup = accentuation?.takeIf { it.isNotBlank() },
    rhymeScheme = null,
)

fun parseArsPoeticaJson(json: String): List<ArsPoeticaEntryDto> {
    val array = JSONArray(json)
    return buildList {
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            add(
                ArsPoeticaEntryDto(
                    author = obj.optString("author", null),
                    title = obj.optString("title", null),
                    poemText = obj.getString("poem_text"),
                    accentuation = obj.optString("accentuation", null),
                )
            )
        }
    }
}