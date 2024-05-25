package com.example.myemotion.db.dao

import android.content.ContentValues
import android.database.Cursor
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

    fun getStato(): Map<String, Int> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT nomeEmozione, COUNT(id) as numerovolteprovata FROM stato_emozionale",
            null
        )
        val statoemozioni = mutableMapOf<String, Int>()

        while (cursor.moveToNext()) {
            val nome = cursor.getString(cursor.run { getColumnIndex("nomeEmozione") })
            val numerovolteprovata =
                cursor.getInt(cursor.run { getColumnIndex("numerovolteprovata") })

            statoemozioni[nome] = numerovolteprovata
        }

        cursor.close()
        return statoemozioni
    }
}
