package com.example.myemotion.db.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myemotion.MainActivity
import com.example.myemotion.db.dao.ConsiglioDao
import com.example.myemotion.db.dao.EmozioneDao
import com.example.myemotion.db.dao.StatoEmozionaleDao

class EmotionDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
val context = context
    companion object {
        private const val DATABASE_NAME = "emotion_database"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        if (context is MainActivity) {
        db.execSQL("CREATE TABLE emozioni (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "descrizione TEXT" +
                ")")

        db.execSQL("CREATE TABLE consigli (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "consiglio TEXT," +
                "idEmozione INTEGER," +
                "numDiVolteVisualizzata INTEGER," +
                "FOREIGN KEY(idEmozione) REFERENCES emozioni(id) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")")

        db.execSQL("CREATE TABLE stato_emozionale (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idEmozione INTEGER," +
                "nomeEmozione TEXT," +
                "intensita INTEGER," +
                "nota TEXT," +
                "data DATE," +
                "FOREIGN KEY(idEmozione) REFERENCES emozioni(id) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")")}
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }


    fun getEmozioneDao(): EmozioneDao {
        return EmozioneDao(this)
    }

    fun getConsiglioDao(): ConsiglioDao {
        return ConsiglioDao(this)
    }

    fun getStatoEmozionaleDao(): StatoEmozionaleDao {
        return StatoEmozionaleDao(this)
    }
}
