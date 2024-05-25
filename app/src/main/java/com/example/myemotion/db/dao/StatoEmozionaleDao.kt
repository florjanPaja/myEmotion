package com.example.myemotion.db.dao

import android.content.ContentValues
import android.database.Cursor
import com.example.myemotion.db.database.EmotionDatabaseHelper
import com.example.myemotion.db.entity.StatoEmozionale
import java.util.*

class StatoEmozionaleDao(private val dbHelper: EmotionDatabaseHelper) {

    fun inserisciStatoEmozionale(statoEmozionale: StatoEmozionale): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("idEmozione", statoEmozionale.idEmozione)
            put("nomeEmozione", statoEmozionale.nomeEmozione)
            put("intensita", statoEmozionale.intensita)
            put("nota", statoEmozionale.nota)
            put("data", Date().time)
        }
        return db.insert("stato_emozionale", null, values)
    }

    fun getStatoYEAR(): Map<String, Int> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT nomeEmozione, COUNT(id) as numerovolteprovata " +
                    "FROM stato_emozionale " +
                    "WHERE strftime('%Y', data) = strftime('%Y', 'now') " +
                    "GROUP BY nomeEmozione " +
                    "ORDER BY numerovolteprovata DESC",
            null
        )
        return ottieniMappaDaCursor(cursor)
    }

    fun getStatoWEEK(): Map<String, Int> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT nomeEmozione, COUNT(id) as numerovolteprovata " +
                    "FROM stato_emozionale " +
                    "WHERE strftime('%W', data) = strftime('%W', 'now') " +
                    "AND strftime('%Y', data) = strftime('%Y', 'now') " +
                    "GROUP BY nomeEmozione " +
                    "ORDER BY numerovolteprovata DESC",
            null
        )
        return ottieniMappaDaCursor(cursor)
    }

    fun getStatoMONTH(): Map<String, Int> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT nomeEmozione, COUNT(id) as numerovolteprovata " +
                    "FROM stato_emozionale " +
                    "WHERE strftime('%m', data) = strftime('%m', 'now') " +
                    "AND strftime('%Y', data) = strftime('%Y', 'now') " +
                    "GROUP BY nomeEmozione " +
                    "ORDER BY numerovolteprovata DESC",
            null
        )
        return ottieniMappaDaCursor(cursor)
    }

    fun getStatoMaggiore(): String {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT nomeEmozione, COUNT(nomeEmozione) as numerovolteprovata " +
                    "FROM stato_emozionale " +
                    "GROUP BY nomeEmozione " +
                    "ORDER BY numerovolteprovata DESC " +
                    "LIMIT 1",
            null
        )
        var nome = ""

        if (cursor.moveToNext()) {
            nome = cursor.getString(cursor.run { getColumnIndex("nomeEmozione") })
        }

        cursor.close()
        return nome
    }

    private fun ottieniMappaDaCursor(cursor: Cursor): Map<String, Int> {
        val statoemozioni = mutableMapOf<String, Int>()

        while (cursor.moveToNext()) {
            val nome = cursor.getString(cursor.run { getColumnIndex("nomeEmozione") })
            val numerovolteprovata = cursor.getInt(cursor.run { getColumnIndex("numerovolteprovata") })
            statoemozioni[nome] = numerovolteprovata
        }

        cursor.close()
        return statoemozioni
    }
}
