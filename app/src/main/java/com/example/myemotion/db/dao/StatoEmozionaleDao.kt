package com.example.myemotion.db.dao

import android.content.ContentValues
import com.example.myemotion.db.database.EmotionDatabaseHelper
import com.example.myemotion.db.entity.StatoEmozionale

class StatoEmozionaleDao(dbHelper: EmotionDatabaseHelper) {
    private val dbHelper: EmotionDatabaseHelper = dbHelper

    fun inserisciStatoEmozionale(statoEmozionale: StatoEmozionale): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("idEmozione", statoEmozionale.idEmozione)
            put("nomeEmozione", statoEmozionale.nomeEmozione)
            put("intensita", statoEmozionale.intensita)
            put("nota", statoEmozionale.nota)
        }
        return db.insert("stato_emozionale", null, values)
    }

    // Aggiungi altre funzioni DAO per gli stati emozionali se necessario
}
