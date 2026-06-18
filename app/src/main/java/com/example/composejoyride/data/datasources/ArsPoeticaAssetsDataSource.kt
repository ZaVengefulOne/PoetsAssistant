package com.example.composejoyride.data.datasources

import android.content.Context
import com.example.composejoyride.data.entitites.Poem
import com.example.composejoyride.data.parcers.parseArsPoeticaJson
import com.example.composejoyride.data.parcers.toEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArsPoeticaAssetsDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun readJson(): String = context.assets.open(ASSET_PATH).bufferedReader().use { it.readText() }

    suspend fun loadPoems(): List<Poem> = withContext(Dispatchers.IO) {
        parseArsPoeticaJson(readJson()).mapIndexed { i, dto -> dto.toEntity(i) }
    }

    companion object {
        const val ASSET_PATH = "arspoetica/arspoetica.json"
        const val DATASET_VERSION = "1.0"
    }
}