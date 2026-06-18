package com.example.composejoyride.di

import com.example.composejoyride.data.repositories.interfaces.IPoemRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
@EntryPoint
@InstallIn(SingletonComponent::class)
interface PoemWidgetEntryPoint {
    fun poemRepository(): IPoemRepository
}