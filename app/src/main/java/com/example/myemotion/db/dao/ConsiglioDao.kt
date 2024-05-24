package com.example.myemotion.db.dao
import android.content.ContentValues
import com.example.myemotion.db.database.EmotionDatabaseHelper
import com.example.myemotion.db.entity.Consiglio

class ConsiglioDao(dbHelper: EmotionDatabaseHelper) {
    private val dbHelper: EmotionDatabaseHelper = dbHelper

    fun inserisciConsiglio(consiglio: Consiglio): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("consiglio", consiglio.consiglio)
            put("idEmozione", consiglio.idEmozione)
            put("numDiVolteVisualizzata", consiglio.numDiVolteVisualizzata)
        }
        return db.insert("consigli", null, values)
    }

    fun showConsiglio(idEmozione: Int): String? {
        val db = dbHelper.writableDatabase
        var consiglio: String? = null
        db.beginTransaction()
        try {
            // Seleziona il consiglio con il numero di volte visualizzato più basso per l'emozione specificata
            val cursor = db.rawQuery("SELECT consiglio, numDiVolteVisualizzata FROM consigli WHERE idEmozione = ? ORDER BY numDiVolteVisualizzata ASC LIMIT 1", arrayOf(idEmozione.toString()))

            if (cursor.moveToFirst()) {
                consiglio = cursor.getString(cursor.getColumnIndexOrThrow("consiglio"))
                val numDiVolteVisualizzata = cursor.getInt(cursor.getColumnIndexOrThrow("numDiVolteVisualizzata"))

                // Incrementa il numero di volte visualizzata
                val values = ContentValues().apply {
                    put("numDiVolteVisualizzata", numDiVolteVisualizzata + 1)
                }
                db.update("consigli", values, "idEmozione = ? AND consiglio = ?", arrayOf(idEmozione.toString(), consiglio))
            }
            cursor.close()
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.endTransaction()
        }
        return consiglio
    }



}
