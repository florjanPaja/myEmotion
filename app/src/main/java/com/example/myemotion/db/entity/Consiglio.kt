package com.example.myemotion.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "consigli",
    foreignKeys = [ForeignKey(
        entity = Emozione::class,
        parentColumns = ["id"],
        childColumns = ["idEmozione"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class Consiglio(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val consiglio: String,
    val idEmozione: Int,
    val numDiVolteVisualizzata: Int
)
