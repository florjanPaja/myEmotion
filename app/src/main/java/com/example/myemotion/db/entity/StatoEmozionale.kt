package com.example.myemotion.db.entity

import java.util.Date

data class StatoEmozionale(
    val id: Int = 0,
    val idEmozione: Int,
    val nomeEmozione: String,
    val intensita: Int,
    val nota: String,
    val data: Date
)
