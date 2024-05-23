package com.example.myemotion.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emozioni")
data class Emozione(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val descrizione: String
)
