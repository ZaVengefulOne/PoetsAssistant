package com.example.composejoyride.data.entitites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "poems")
data class Poem(
    @PrimaryKey val id: Int,
    val author: String?,
    val title: String?,
    val poemText: String,
    val accentuationMarkup: String?,      // для scansion tool
    val rhymeScheme: String?,
    val isStarred: Boolean = false,
)