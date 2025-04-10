package com.plm.junglejumble.database.dao

import androidx.room.*
import com.plm.junglejumble.database.models.Score

@Dao
interface ScoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(score: Score)

    @Query("SELECT * FROM Score")
    suspend fun getAllScores(): List<Score>

    @Delete
    suspend fun delete(score: Score)
}
