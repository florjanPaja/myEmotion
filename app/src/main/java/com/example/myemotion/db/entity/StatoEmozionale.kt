package com.example.myemotion.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "stato_emozionale",
    foreignKeys = [ForeignKey(
        entity = Emozione::class,
        parentColumns = ["id"],
        childColumns = ["idEmozione"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class StatoEmozionale(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idEmozione: Int,
    val nomeEmozione: String,
    val intensita: Int,
    val nota: String
)
