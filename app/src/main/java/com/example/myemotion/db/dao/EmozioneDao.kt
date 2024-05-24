package com.example.myemotion.db.dao
import android.content.ContentValues
import android.database.Cursor
import com.example.myemotion.db.database.EmotionDatabaseHelper
import com.example.myemotion.db.entity.Emozione

class EmozioneDao(dbHelper: EmotionDatabaseHelper) {
    private val dbHelper: EmotionDatabaseHelper = dbHelper
    fun inserisciEmozione(emozione: Emozione): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", emozione.nome)
            put("descrizione", emozione.descrizione)
        }
        return db.insert("emozioni", null, values)
    }

    fun getEmozioni(): List<Emozione> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM emozioni", null)
        val emotions = mutableListOf<Emozione>()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.run { getColumnIndex("id") })
            val nome = cursor.getString(cursor.run { getColumnIndex("nome") })
            val descrizione = cursor.getString(cursor.run { getColumnIndex("descrizione") })
            emotions.add(Emozione(id, nome, descrizione))
        }

        cursor.close()
        return emotions
    }
    fun getNomiEmozioni(): Map<String, Int> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT id, nome FROM emozioni", null)
        val nomiEmozioni = mutableMapOf<String, Int>()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.run { getColumnIndex("id") })
            val nome = cursor.getString(cursor.run { getColumnIndex("nome") })
            nomiEmozioni[nome] = id
        }

        cursor.close()
        return nomiEmozioni
    }


}

