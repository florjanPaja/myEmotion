package com.example.myemotion.db.dao

import android.content.ContentValues
import android.database.Cursor
import com.example.myemotion.db.database.EmotionDatabaseHelper
import com.example.myemotion.db.entity.StatoEmozionale
import com.example.myemotion.xgestioneemozioni.EmozioneStatistica
import java.util.Calendar
import java.util.Date

/**
 * La classe StatoEmozionaleDao gestisce le operazioni di accesso e manipolazione dei dati nella tabella dello stato emozionale del database.
 * Fornisce metodi per inserire nuovi record di stato emozionale e per recuperare le statistiche delle emozioni.
 *
 * @property dbHelper L'oggetto EmotionDatabaseHelper utilizzato per accedere al database.
 */
class StatoEmozionaleDao(private val dbHelper: EmotionDatabaseHelper) {

    /**
     * Inserisce un nuovo record di stato emozionale nella tabella.
     *
     * @param statoEmozionale L'oggetto StatoEmozionale da inserire.
     * @return L'ID della riga inserita nel database.
     */
    fun inserisciStatoEmozionale(statoEmozionale: StatoEmozionale): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("idEmozione", statoEmozionale.idEmozione)
            put("nomeEmozione", statoEmozionale.nomeEmozione)
            put("intensita", statoEmozionale.intensita)
            put("nota", statoEmozionale.nota)
            put("data", Date().time) // Inserisce la data corrente come timestamp
        }
        return db.insert("stato_emozionale", null, values)
    }

    /**
     * Recupera il nome dell'emozione che è stata provata il maggior numero di volte.
     *
     * @return Il nome dell'emozione più frequentemente provata.
     */
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

        cursor.close() // Chiude il cursore
        return nome
    }

    /**
     * Recupera lo stato emozionale basato su un periodo specificato e restituisce una lista di statistiche delle emozioni.
     *
     * @param periodo Il periodo di tempo da considerare: "SETTIMANA", "MESE" o "ANNO".
     * @return Una lista di EmozioneStatistica contenente le statistiche per ogni emozione.
     */
    fun getStatoEmozionale(periodo: String): List<EmozioneStatistica> {
        val db = dbHelper.readableDatabase
        // Esegue una query per ottenere tutti i dati senza filtro sulla data
        val cursor: Cursor = db.rawQuery(
            "SELECT nomeEmozione, intensita, data " +
                    "FROM stato_emozionale",
            null
        )
        val result = ottieniMappaDaCursor(cursor, periodo)
        cursor.close() // Chiude il cursore
        return result
    }

    /**
     * Estrae i dati dal cursore e calcola le statistiche delle emozioni basate sul periodo specificato.
     *
     * @param cursor Il cursore contenente i dati dello stato emozionale.
     * @param periodo Il periodo di tempo da considerare: "SETTIMANA", "MESE" o "ANNO".
     * @return Una lista di EmozioneStatistica contenente le statistiche per ogni emozione.
     */
    private fun ottieniMappaDaCursor(cursor: Cursor, periodo: String): List<EmozioneStatistica> {
        // Crea una mappa mutabile per memorizzare le somme delle intensità e i conteggi delle emozioni
        val intensitaEmozioni = mutableMapOf<String, Int>()
        val conteggioEmozioni = mutableMapOf<String, Int>()

        // Ottieni le informazioni sulla data corrente
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentYear = calendar.get(Calendar.YEAR)
        val currentWeek = calendar.get(Calendar.WEEK_OF_YEAR)

        // Itera attraverso le righe del cursore
        while (cursor.moveToNext()) {
            val nome = cursor.getString(cursor.getColumnIndexOrThrow("nomeEmozione"))
            val data = cursor.getLong(cursor.getColumnIndexOrThrow("data"))
            val intensita = cursor.getInt(cursor.getColumnIndexOrThrow("intensita"))

            // Converti il timestamp in un oggetto Date
            val date = Date(data)

            val dateCalendar = Calendar.getInstance().apply { time = date }
            val month = dateCalendar.get(Calendar.MONTH) + 1
            val year = dateCalendar.get(Calendar.YEAR)
            val week = dateCalendar.get(Calendar.WEEK_OF_YEAR)

            // Controlla il periodo e incrementa i conteggi e le somme delle intensità se soddisfa i criteri
            when (periodo) {
                "SETTIMANA" -> {
                    if (week == currentWeek && year == currentYear) {
                        intensitaEmozioni[nome] =
                            intensitaEmozioni.getOrDefault(nome, 0) + intensita
                        conteggioEmozioni[nome] = conteggioEmozioni.getOrDefault(nome, 0) + 1
                    }
                }

                "MESE" -> {
                    if (month == currentMonth && year == currentYear) {
                        intensitaEmozioni[nome] =
                            intensitaEmozioni.getOrDefault(nome, 0) + intensita
                        conteggioEmozioni[nome] = conteggioEmozioni.getOrDefault(nome, 0) + 1
                    }
                }

                "ANNO" -> {
                    if (year == currentYear) {
                        intensitaEmozioni[nome] =
                            intensitaEmozioni.getOrDefault(nome, 0) + intensita
                        conteggioEmozioni[nome] = conteggioEmozioni.getOrDefault(nome, 0) + 1
                    }
                }
            }
        }

        // Crea una lista di EmozioneStatistica calcolando la media delle intensità
        val risultato = mutableListOf<EmozioneStatistica>()
        for (emozione in intensitaEmozioni.keys) {
            val mediaIntensita =
                intensitaEmozioni[emozione]!!.toDouble() / conteggioEmozioni[emozione]!!
            risultato.add(
                EmozioneStatistica(
                    emozione,
                    mediaIntensita,
                    conteggioEmozioni[emozione]!!
                )
            )
        }

        return risultato // Restituisce la lista di statistiche delle emozioni
    }
}
