package com.example.composejoyride.data.repositories

import com.example.composejoyride.data.network.interactors.interfaces.IPoemInteractor
import com.example.composejoyride.data.repositories.interfaces.IPoemRepository
import com.example.composejoyride.data.entitites.Poem
import javax.inject.Inject

class PoemRepository @Inject constructor(
    private val interactor: IPoemInteractor,
) : IPoemRepository {
    override suspend fun ensurePoemsLoaded() = interactor.ensurePoemsLoaded()
    override suspend fun getPoemOfDay(): Poem? = interactor.getPoemOfDay()
    override suspend fun getPoem(poemId: Int): Poem? = interactor.getPoem(poemId)
    override suspend fun toggleStar(poemId: Int) = interactor.toggleStar(poemId)
    override suspend fun regeneratePoemOfDay(): Poem? = interactor.regeneratePoemOfDay()
}