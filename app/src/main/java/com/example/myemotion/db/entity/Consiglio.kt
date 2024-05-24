package com.example.myemotion.db.entity
data class Consiglio(
    val id: Int = 0,
    val consiglio: String,
    val idEmozione: Int,
    val numDiVolteVisualizzata: Int
)
