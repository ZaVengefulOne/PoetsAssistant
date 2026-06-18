package com.example.composejoyride.data.datasources

import android.content.Context
import com.example.composejoyride.data.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class PoemOfDayStore @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val prefs =
        context.getSharedPreferences(Constants.PREFERENCES_POEM, Context.MODE_PRIVATE)

    fun getOverrideIdForToday(): Int? {
        if (prefs.getString(KEY_DATE, null) != todayKey()) return null
        return prefs.getInt(KEY_POEM_ID, -1).takeIf { it >= 0 }
    }

    fun saveOverrideForToday(poemId: Int) {
        prefs.edit(commit = true) {
            putString(KEY_DATE, todayKey())
                .putInt(KEY_POEM_ID, poemId)
        }
    }

    private fun todayKey(): String {
        val date = LocalDate.now()
        return "${date.year}-${date.dayOfYear}"
    }

    fun getDatasetVersion(): String? = prefs.getString(KEY_DATASET_VERSION, null)
    fun saveDatasetVersion(version: String) {
        prefs.edit(commit = true) { putString(KEY_DATASET_VERSION, version) }
    }

    companion object {
        private const val KEY_DATE = "poem_of_day_date"
        private const val KEY_DATASET_VERSION = "dataset_version"
        private const val KEY_POEM_ID = "poem_of_day_override_id"
    }
}