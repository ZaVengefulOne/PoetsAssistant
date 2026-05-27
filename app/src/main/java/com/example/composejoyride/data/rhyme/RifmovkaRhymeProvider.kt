package com.example.composejoyride.data.rhyme

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.net.URLEncoder

class RifmovkaRhymeProvider : IRhymeProvider {

    override suspend fun getRhymes(word: String, stressIndex: Int): List<String> = withContext(Dispatchers.IO) {
        val trimmed = word.trim()
        require(trimmed.isNotEmpty()) { "Word must not be blank" }
        require(stressIndex >= 0) { "stressIndex must be non-negative" }

        val pathSegment = buildPathSegment(trimmed, stressIndex)
        val encodedSegment = URLEncoder.encode(pathSegment, Charsets.UTF_8.name())
        val document = Jsoup.connect(RHYMES_BASE_URL + encodedSegment)
            .userAgent(USER_AGENT)
            .timeout(TIMEOUT_MS)
            .followRedirects(true)
            .get()

        parseRhymeWordsFromDocument(document, trimmed)
    }

    internal fun buildPathSegment(word: String, stressIndex: Int): String = "$word$stressIndex"

    internal fun parseRhymeWordsFromDocument(document: org.jsoup.nodes.Document, query: String): List<String> {
        val fromVowelBlock = document.select("ul.vowelBlock > li")
            .mapNotNull { element -> element.text().trim().takeIf { it.isNotEmpty() } }
            .filter { isValidRhyme(it, query) }

        if (fromVowelBlock.isNotEmpty()) {
            return fromVowelBlock.distinct()
        }

        return document.select("ul.vowelBlock li")
            .mapNotNull { element -> element.text().trim().takeIf { it.isNotEmpty() } }
            .filter { isValidRhyme(it, query) }
            .distinct()
    }

    private fun isValidRhyme(candidate: String, query: String): Boolean {
        if (candidate.equals(query, ignoreCase = true)) return false
        if (candidate.length < 2) return false
        if (candidate.lowercase() in IGNORED_TEXT) return false
        return true
    }

    private companion object {
        const val RHYMES_BASE_URL = "https://rifmovka.ru/rifma/"
        const val TIMEOUT_MS = 15_000
        const val USER_AGENT =
            "Mozilla/5.0 (Linux; Android 13) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36"

        val IGNORED_TEXT = setOf(
            "наверх",
            "загрузка...",
            "похожие слова",
            "×",
        )
    }
}
