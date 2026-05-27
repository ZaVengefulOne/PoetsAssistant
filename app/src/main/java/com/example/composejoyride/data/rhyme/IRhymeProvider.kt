package com.example.composejoyride.data.rhyme

interface IRhymeProvider {
    suspend fun getRhymes(word: String, stressIndex: Int): List<String>
}
