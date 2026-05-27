package com.example.composejoyride

import com.example.composejoyride.data.rhyme.RifmovkaRhymeProvider
import org.jsoup.Jsoup
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RifmovkaRhymeProviderTest {

    private val provider = RifmovkaRhymeProvider()

    @Test
    fun `buildPathSegment appends stress index`() {
        assertEquals("дорога0", provider.buildPathSegment("дорога", 0))
        assertEquals("дорога1", provider.buildPathSegment("дорога", 1))
    }

    @Test
    fun `parses vowelBlock list items and excludes query word`() {
        val html = """
            <html><body>
              <ul class="vowelBlock">
                <li class="vis adj">недорога</li>
                <li class="vis adj">строга</li>
                <li class="vis adj">дорога</li>
              </ul>
              <a href="/rifma/тропинка">тропинка</a>
            </body></html>
        """.trimIndent()

        val words = provider.parseRhymeWordsFromDocument(Jsoup.parse(html), "дорога")

        assertEquals(listOf("недорога", "строга"), words)
    }

    @Test
    fun `parses vowelBlock with stressed syllable in bold`() {
        val html = """
            <html><body>
              <ul class="vowelBlock">
                <li class="vis adj">недорог<b>а</b></li>
                <li class="vis adj">строг<b>а</b></li>
              </ul>
            </body></html>
        """.trimIndent()

        val words = provider.parseRhymeWordsFromDocument(Jsoup.parse(html), "дорога")

        assertTrue(words.contains("недорога"))
        assertTrue(words.contains("строга"))
    }
}
