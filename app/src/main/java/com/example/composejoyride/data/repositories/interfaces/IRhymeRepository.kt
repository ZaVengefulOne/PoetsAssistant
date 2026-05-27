package com.example.composejoyride.data.repositories.interfaces

interface IRhymeRepository {
    suspend fun getRhymes(word: String, stressIndex: Int): List<String>
}
