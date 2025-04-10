package com.plm.junglejumble.database.dao

import androidx.room.*
import com.plm.junglejumble.database.models.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM User")
    suspend fun getAllUsers(): List<User>

    @Delete
    suspend fun delete(user: User)
}
