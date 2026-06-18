package com.example.composejoyride.data.utils

import com.example.composejoyride.data.entitites.Poem

fun Poem.displayTitle(): String =
    title?.takeIf { it.isNotBlank() }
        ?: poemText.lineSequence()
            .firstOrNull { it.isNotBlank() }
            ?.take(50)
            ?.let { if (it.length == 50) "$it…" else it }
        ?: "Стих дня"

fun Poem.displayAuthor(): String? = author?.takeIf { it.isNotBlank() }

fun Poem.widgetPreview(maxLines: Int = 3, maxChars: Int = 120): String {
    val header = displayAuthor()?.let { "$it\n" } ?: ""
    return header + poemText
        .lineSequence()
        .filter { it.isNotBlank() }
        .take(maxLines)
        .joinToString("\n")
        .take(maxChars)
        .let { if (it.length == maxChars) "$it…" else it }
}