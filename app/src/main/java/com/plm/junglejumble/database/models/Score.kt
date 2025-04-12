package com.plm.junglejumble.database.models

import androidx.room.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["ownerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["ownerId"])]
)
data class Score(
    @PrimaryKey(autoGenerate = true) val scoreId: Int = 0,
    val score: Int,
    val ownerId: Int
)
