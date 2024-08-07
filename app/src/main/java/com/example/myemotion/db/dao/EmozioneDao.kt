package com.example.myemotion.db.dao

import android.content.ContentValues
import android.database.Cursor
import com.example.myemotion.db.database.EmotionDatabaseHelper
import com.example.myemotion.db.entity.Emozione

/**
 * La classe EmozioneDao gestisce le operazioni di accesso e manipolazione dei dati nella tabella delle emozioni del database.
 * Fornisce metodi per inserire nuove emozioni e per recuperare le emozioni esistenti.
 *
 * @property dbHelper L'oggetto EmotionDatabaseHelper utilizzato per accedere al database.
 */
class EmozioneDao(dbHelper: EmotionDatabaseHelper) {
    private val dbHelper: EmotionDatabaseHelper = dbHelper

    /**
     * Inserisce una nuova emozione nella tabella delle emozioni.
     *
     * @param emozione L'oggetto Emozione da inserire.
     * @return L'ID della riga inserita nel database.
     */
    fun inserisciEmozione(emozione: Emozione): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", emozione.nome) // Inserisce il nome dell'emozione
            put("descrizione", emozione.descrizione) // Inserisce la descrizione dell'emozione
        }
        return db.insert("emozioni", null, values) // Inserisce il record e restituisce l'ID
    }

    /**
     * Recupera tutte le emozioni dalla tabella delle emozioni.
     *
     * @return Una lista di oggetti Emozione.
     */
    fun getEmozioni(): List<Emozione> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM emozioni", null)
        val emotions = mutableListOf<Emozione>()

        while (cursor.moveToNext()) {
            // Estrae i dati del cursore e crea un oggetto Emozione per ogni riga
            val id = cursor.getInt(cursor.run { getColumnIndex("id") })
            val nome = cursor.getString(cursor.run { getColumnIndex("nome") })
            val descrizione = cursor.getString(cursor.run { getColumnIndex("descrizione") })
            emotions.add(Emozione(id, nome, descrizione))
        }

        cursor.close() // Chiude il cursore per liberare risorse
        return emotions // Restituisce la lista delle emozioni
    }

    /**
     * Recupera una mappa dei nomi delle emozioni con i loro rispettivi ID.
     *
     * @return Una mappa in cui la chiave è il nome dell'emozione e il valore è il suo ID.
     */
    fun getNomiEmozioni(): Map<String, Int> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT id, nome FROM emozioni", null)
        val nomiEmozioni = mutableMapOf<String, Int>()

        while (cursor.moveToNext()) {
            // Estrae i dati del cursore e crea una mappa con i nomi e gli ID delle emozioni
            val id = cursor.getInt(cursor.run { getColumnIndex("id") })
            val nome = cursor.getString(cursor.run { getColumnIndex("nome") })
            nomiEmozioni[nome] = id
        }

        cursor.close() // Chiude il cursore per liberare risorse
        return nomiEmozioni // Restituisce la mappa dei nomi e degli ID delle emozioni
    }
}
