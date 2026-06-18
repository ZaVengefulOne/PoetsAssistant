package com.example.composejoyride.data.network.interactors.interfaces

import com.example.composejoyride.data.entitites.Poem

interface IPoemInteractor {
    suspend fun getPoemOfDay(): Poem?
    suspend fun ensurePoemsLoaded()
    suspend fun toggleStar(poemId: Int)
    suspend fun regeneratePoemOfDay(): Poem?
}