package com.example.composejoyride.data.repositories.interfaces

import com.example.composejoyride.data.entitites.Poem

interface IPoemRepository {
    suspend fun ensurePoemsLoaded()
    suspend fun getPoemOfDay(): Poem?
    suspend fun getPoem(poemId: Int): Poem?
    suspend fun toggleStar(poemId: Int)
    suspend fun regeneratePoemOfDay(): Poem?
}