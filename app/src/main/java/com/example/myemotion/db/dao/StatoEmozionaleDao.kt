package com.example.myemotion.db.dao

import android.content.ContentValues
import android.database.Cursor
import com.example.myemotion.db.database.EmotionDatabaseHelper
import com.example.myemotion.db.entity.StatoEmozionale
import java.util.*
import java.text.SimpleDateFormat


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


    fun getStatoEmozionale(periodo: String): Map<String, Int> {
        val db = dbHelper.readableDatabase
        // Query to get all data without date filtering
        val cursor: Cursor = db.rawQuery(
            "SELECT nomeEmozione, data " +
                    "FROM stato_emozionale",
            null
        )
        val result = ottieniMappaDaCursor(cursor, periodo)
        cursor.close()
        return result
    }


    private fun ottieniMappaDaCursor(cursor: Cursor, periodo: String): Map<String, Int> {
        // Crea una mappa mutabile per memorizzare i conteggi delle emozioni
        val statoemozioni = mutableMapOf<String, Int>()

        // Ottieni le informazioni sulla data corrente
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentYear = calendar.get(Calendar.YEAR)
        val currentWeek = calendar.get(Calendar.WEEK_OF_YEAR)

        // Itera attraverso le righe del cursore
        while (cursor.moveToNext()) {
            val nome = cursor.getString(cursor.getColumnIndexOrThrow("nomeEmozione"))
            val data = cursor.getLong(cursor.getColumnIndexOrThrow("data"))

            // Converti il timestamp in un oggetto Date
            val date = Date(data)

            val dateCalendar = Calendar.getInstance().apply { time = date }
            val month = dateCalendar.get(Calendar.MONTH) + 1
            val year = dateCalendar.get(Calendar.YEAR)
            val week = dateCalendar.get(Calendar.WEEK_OF_YEAR)

            // Controlla il periodo e incrementa il conteggio dell'emozione se soddisfa i criteri
            when (periodo) {
                "SETTIMANA" -> {
                    if (week == currentWeek && year == currentYear) {
                        statoemozioni[nome] = statoemozioni.getOrDefault(nome, 0) + 1
                    }
                }

                "MESE" -> {
                    if (month == currentMonth && year == currentYear) {
                        statoemozioni[nome] = statoemozioni.getOrDefault(nome, 0) + 1
                    }
                }

                "ANNO" -> {
                    if (year == currentYear) {
                        statoemozioni[nome] = statoemozioni.getOrDefault(nome, 0) + 1
                    }
                }
            }
        }

        cursor.close()
        return statoemozioni
    }


}
