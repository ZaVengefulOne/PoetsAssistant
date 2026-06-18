package com.example.composejoyride.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.composejoyride.data.entitites.Poem

@Dao
interface PoemsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(poems: List<Poem>)

    @Query("SELECT COUNT(*) FROM poems")
    suspend fun count(): Int

    @Query("SELECT * FROM poems WHERE id = :id LIMIT 1")
    suspend fun getPoemById(id: Int): Poem?

    @Query("UPDATE poems SET isStarred = :isStarred WHERE id = :id")
    suspend fun setStarred(id: Int, isStarred: Boolean)

    @Query("DELETE FROM poems")
    suspend fun deleteAll()

    @Query("SELECT * FROM poems WHERE isStarred = 1 ORDER BY id")
    suspend fun getStarredPoems(): List<Poem>
}