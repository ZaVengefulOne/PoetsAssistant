package com.example.composejoyride.data.network.interactors

import com.example.composejoyride.data.dao.PoemsDao
import com.example.composejoyride.data.datasources.PoemOfDayStore
import com.example.composejoyride.data.datasources.ArsPoeticaAssetsDataSource
import com.example.composejoyride.data.network.interactors.interfaces.IPoemInteractor
import com.example.composejoyride.data.entitites.Poem
import javax.inject.Inject

class PoemInteractor @Inject constructor(
    private val dao: PoemsDao,
    private val arsPoeticaAssetsDataSource: ArsPoeticaAssetsDataSource,
    private val poemOfDayStore: PoemOfDayStore,
) : IPoemInteractor {

    override suspend fun ensurePoemsLoaded() {
        val storedVersion = poemOfDayStore.getDatasetVersion()
        if (dao.count() > 0 && storedVersion == ArsPoeticaAssetsDataSource.DATASET_VERSION) {
            return
        }
        dao.deleteAll()
        val poems = arsPoeticaAssetsDataSource.loadPoems()
        if (poems.isEmpty()) error("Ars Poetica dataset is empty")
        dao.insertAll(poems)
        poemOfDayStore.saveDatasetVersion(ArsPoeticaAssetsDataSource.DATASET_VERSION)
    }

    override suspend fun getPoemOfDay(): Poem? {
        ensurePoemsLoaded()
        val count = dao.count()
        if (count == 0) return null
        val id = poemOfDayStore.getOverrideIdForToday() ?: poemOfDayId(count)
        return dao.getPoemById(id)
    }
    override suspend fun regeneratePoemOfDay(): Poem? {
        ensurePoemsLoaded()
        val count = dao.count()
        if (count == 0) return null
        val randomId = (0 until count).random()
        poemOfDayStore.saveOverrideForToday(randomId)
        return dao.getPoemById(randomId)
    }

    override suspend fun getStarredPoems(): List<Poem> {
        ensurePoemsLoaded()
        return dao.getStarredPoems()
    }

    override suspend fun toggleStar(poemId: Int) {
        val poem = dao.getPoemById(poemId) ?: return
        dao.setStarred(poemId, !poem.isStarred)
    }

    override suspend fun getPoem(poemId: Int): Poem? {
        return dao.getPoemById(poemId)
    }

    private fun poemOfDayId(count: Int): Int {
        val dayIndex = java.time.LocalDate.now().dayOfYear - 1 // 0..364
        return dayIndex % count
    }
}