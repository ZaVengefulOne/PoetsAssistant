package com.example.composejoyride.di.models

/**
 * @param stress Reserved for a future offline rhyme dictionary with vowel stress support.
 */
data class Rhyme(
    val text: String,
    @Deprecated("Stress selection is not supported by the current rhyme provider")
    val stress: Int = 0,
)
