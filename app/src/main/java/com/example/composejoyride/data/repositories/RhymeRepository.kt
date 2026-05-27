package com.example.composejoyride.data.repositories

import com.example.composejoyride.data.repositories.interfaces.IRhymeRepository
import com.example.composejoyride.data.rhyme.IRhymeProvider
import javax.inject.Inject

class RhymeRepository @Inject constructor(
    private val rhymeProvider: IRhymeProvider,
) : IRhymeRepository {
    override suspend fun getRhymes(word: String, stressIndex: Int): List<String> =
        rhymeProvider.getRhymes(word, stressIndex)
}
