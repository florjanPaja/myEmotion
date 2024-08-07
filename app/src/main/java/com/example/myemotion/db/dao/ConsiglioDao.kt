package com.example.myemotion.db.dao

import android.content.ContentValues
import com.example.myemotion.db.database.EmotionDatabaseHelper
import com.example.myemotion.db.entity.Consiglio

/**
 * La classe ConsiglioDao gestisce le operazioni di accesso e manipolazione dei dati nella tabella dei consigli del database.
 * Fornisce metodi per inserire nuovi consigli e per recuperare consigli specifici.
 *
 * @property dbHelper L'oggetto EmotionDatabaseHelper utilizzato per accedere al database.
 */
class ConsiglioDao(private val dbHelper: EmotionDatabaseHelper) {

    /**
     * Inserisce un nuovo consiglio nella tabella dei consigli del database.
     *
     * @param consiglio L'oggetto Consiglio da inserire.
     * @return L'ID della riga inserita nel database.
     */
    fun inserisciConsiglio(consiglio: Consiglio): Long {
        // Ottiene una scrittura del database
        val db = dbHelper.writableDatabase

        // Crea un ContentValues per contenere i dati del consiglio
        val values = ContentValues().apply {
            put("consiglio", consiglio.consiglio)
            put("idEmozione", consiglio.idEmozione)
            put("numDiVolteVisualizzata", consiglio.numDiVolteVisualizzata)
        }

        // Inserisce il consiglio nella tabella e restituisce l'ID della riga inserita
        return db.insert("consigli", null, values)
    }

    /**
     * Recupera un consiglio per un'emozione specifica e aggiorna il numero di volte visualizzate.
     * Seleziona il consiglio con il numero di volte visualizzato più basso.
     *
     * @param idEmozione L'ID dell'emozione per cui recuperare il consiglio.
     * @return Il testo del consiglio selezionato, oppure null se nessun consiglio è trovato.
     */
    fun showConsiglio(idEmozione: Int): String? {
        val db = dbHelper.writableDatabase
        var consiglio: String? = null

        // Inizia una transazione per garantire l'atomicità delle operazioni
        db.beginTransaction()
        try {
            // Seleziona il consiglio con il numero di volte visualizzato più basso per l'emozione specificata
            val cursor = db.rawQuery(
                "SELECT consiglio, numDiVolteVisualizzata FROM consigli WHERE idEmozione = ? ORDER BY numDiVolteVisualizzata ASC LIMIT 1",
                arrayOf(idEmozione.toString())
            )

            // Se il cursore ha dei risultati
            if (cursor.moveToFirst()) {
                // Ottiene il testo del consiglio e il numero di volte visualizzate
                consiglio = cursor.getString(cursor.getColumnIndexOrThrow("consiglio"))
                val numDiVolteVisualizzata =
                    cursor.getInt(cursor.getColumnIndexOrThrow("numDiVolteVisualizzata"))

                // Incrementa il numero di volte visualizzata
                val values = ContentValues().apply {
                    put("numDiVolteVisualizzata", numDiVolteVisualizzata + 1)
                }
                db.update(
                    "consigli",
                    values,
                    "idEmozione = ? AND consiglio = ?",
                    arrayOf(idEmozione.toString(), consiglio)
                )
            }

            cursor.close()
            db.setTransactionSuccessful() // Segna la transazione come riuscita
        } catch (e: Exception) {
            e.printStackTrace() // Stampa l'eccezione se qualcosa va storto
        } finally {
            db.endTransaction() // Termina la transazione
        }

        // Restituisce il consiglio recuperato
        return consiglio
    }
}
