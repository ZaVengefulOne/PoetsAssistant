package com.example.composejoyride.data.widget

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.glance.currentState
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.example.composejoyride.data.entitites.Poem
import com.example.composejoyride.data.utils.displayTitle
import com.example.composejoyride.data.utils.widgetPreview
import com.example.composejoyride.di.PoemWidgetEntryPoint
import com.example.composejoyride.ui.widgets.PoemWidgetContent
import dagger.hilt.android.EntryPointAccessors

class PoemWidget : GlanceAppWidget() {
    override val stateDefinition = PreferencesGlanceStateDefinition
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        // Первичная загрузка при добавлении виджета
        val poem = loadPoem(context)
        if (poem != null) {
            updateWidgetState(context, id, poem)
        }
        provideContent {
            val state = currentState<Preferences>()
            PoemWidgetContent(
                title = state[PoemWidgetKeys.TITLE] ?: "Стих дня",
                preview = state[PoemWidgetKeys.PREVIEW]
                    ?: "Откройте приложение, чтобы загрузить стих",
            )
        }
    }
    companion object {
        val instance = PoemWidget()
        suspend fun refreshAll(context: Context, poem: Poem) {
            val appContext = context.applicationContext
            val manager = GlanceAppWidgetManager(appContext)
            val glanceIds = manager.getGlanceIds(PoemWidget::class.java)
            glanceIds.forEach { glanceId ->
                updateWidgetState(appContext, glanceId, poem)
            }
            instance.updateAll(appContext)
        }
        private suspend fun updateWidgetState(
            context: Context,
            glanceId: GlanceId,
            poem: Poem,
        ) {
            updateAppWidgetState(context, glanceId) { prefs: MutablePreferences ->
                prefs[PoemWidgetKeys.TITLE] = poem.displayTitle()
                prefs[PoemWidgetKeys.PREVIEW] = poem.widgetPreview()
            }
        }
        private suspend fun loadPoem(context: Context): Poem? {
            val repository = EntryPointAccessors.fromApplication(
                context.applicationContext,
                PoemWidgetEntryPoint::class.java,
            ).poemRepository()
            return runCatching { repository.getPoemOfDay() }.getOrNull()
        }
    }
}