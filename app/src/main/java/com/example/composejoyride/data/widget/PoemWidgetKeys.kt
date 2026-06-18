package com.example.composejoyride.data.widget

import androidx.datastore.preferences.core.stringPreferencesKey

object PoemWidgetKeys {
    val TITLE = stringPreferencesKey("poem_title")
    val PREVIEW = stringPreferencesKey("poem_preview")
}