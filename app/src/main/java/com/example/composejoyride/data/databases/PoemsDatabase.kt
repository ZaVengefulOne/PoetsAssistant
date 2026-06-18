package com.example.composejoyride.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.composejoyride.data.dao.NotesDao
import com.example.composejoyride.data.dao.PoemsDao
import com.example.composejoyride.data.entitites.Note
import com.example.composejoyride.data.entitites.Poem

@Database(entities = [Poem::class], version = 3, exportSchema = false)
abstract class PoemsDatabase : RoomDatabase() {
    abstract fun poemsDao(): PoemsDao
}