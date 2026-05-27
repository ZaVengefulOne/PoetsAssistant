package com.example.composejoyride.data.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Home


//val CustomFontFamily = FontFamily(Font(R.font.tippytoesbold))

object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Главная",
            icon = Icons.Filled.Home,
            route = NoteGraph.MAIN_SCREEN
        ),
        BottomNavItem(
            label = "Заметки",
            icon = Icons.Filled.EditNote,
            route = NoteGraph.NOTES_SCREEN
        ),
        BottomNavItem(
            label = "Генератор",
            icon = Icons.Filled.Abc,
            route = NoteGraph.GENERATOR_SCREEN
        ),
        BottomNavItem(
            label = "Библиотека",
            icon = Icons.Filled.AutoStories,
            route = NoteGraph.LIBRARY_SCREEN
        ),
        BottomNavItem(
            label = "Аккаунт",
            icon = Icons.Filled.AccountBox,
            route = NoteGraph.PROFILE_SCREEN
        ),
        BottomNavItem(
            label = "Админ",
            icon = Icons.Filled.AdminPanelSettings,
            route = NoteGraph.ADMIN_SCREEN
        )

    )

    const val APP_VERSION = "1.0.6"

    const val PREFERENCES = "vengeful_preferences"
    const val EDIT_KEY = "dark_theme"
    val vowels = setOf(
        'а', 'е', 'ё', 'и', 'о', 'у', 'ы', 'э', 'ю', 'я',
        'А', 'Е', 'Ё', 'И', 'О', 'У', 'Ы', 'Э', 'Ю', 'Я'
    )
}