package com.example.myemotion.db.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myemotion.MainActivity
import com.example.myemotion.db.dao.ConsiglioDao
import com.example.myemotion.db.dao.EmozioneDao
import com.example.myemotion.db.dao.StatoEmozionaleDao

/**
 * La classe EmotionDatabaseHelper gestisce la creazione e l'aggiornamento del database SQLite.
 * Estende SQLiteOpenHelper e fornisce metodi per accedere ai DAO delle diverse tabelle del database.
 *
 * @property context Il contesto dell'applicazione utilizzato per operazioni di database.
 */
class EmotionDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val context: Context = context

    companion object {
        // Nome del database SQLite
        private const val DATABASE_NAME = "emotion_database"

        // Versione del database, usata per gestire aggiornamenti del database
        private const val DATABASE_VERSION = 1
    }

    /**
     * Crea le tabelle nel database quando viene creato per la prima volta.
     *
     * @param db Il database SQLite su cui eseguire le operazioni di creazione delle tabelle.
     */
    override fun onCreate(db: SQLiteDatabase) {
        // Verifica se il contesto è un'istanza di MainActivity
        if (context is MainActivity) {
            // Crea la tabella 'emozioni' con id, nome e descrizione
            db.execSQL(
                "CREATE TABLE emozioni (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nome TEXT," +
                        "descrizione TEXT" +
                        ")"
            )

            // Crea la tabella 'consigli' con id, consiglio, idEmozione e numDiVolteVisualizzata
            // Collega la tabella 'consigli' alla tabella 'emozioni' tramite una chiave esterna
            db.execSQL(
                "CREATE TABLE consigli (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "consiglio TEXT," +
                        "idEmozione INTEGER," +
                        "numDiVolteVisualizzata INTEGER," +
                        "FOREIGN KEY(idEmozione) REFERENCES emozioni(id) ON DELETE CASCADE ON UPDATE CASCADE" +
                        ")"
            )

            // Crea la tabella 'stato_emozionale' con id, idEmozione, nomeEmozione, intensita, nota e data
            // Collega la tabella 'stato_emozionale' alla tabella 'emozioni' tramite una chiave esterna
            db.execSQL(
                "CREATE TABLE stato_emozionale (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "idEmozione INTEGER," +
                        "nomeEmozione TEXT," +
                        "intensita INTEGER," +
                        "nota TEXT," +
                        "data DATE," +
                        "FOREIGN KEY(idEmozione) REFERENCES emozioni(id) ON DELETE CASCADE ON UPDATE CASCADE" +
                        ")"
            )
        }
    }

    /**
     * Gestisce l'aggiornamento del database quando la versione cambia.
     * Attualmente non implementa nessuna operazione.
     *
     * @param db Il database SQLite su cui eseguire le operazioni di aggiornamento.
     * @param oldVersion La versione precedente del database.
     * @param newVersion La nuova versione del database.
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Attualmente non implementa nessuna operazione per l'aggiornamento
    }

    /**
     * Restituisce un'istanza di EmozioneDao per accedere alle operazioni del database per la tabella 'emozioni'.
     *
     * @return Un'istanza di EmozioneDao.
     */
    fun getEmozioneDao(): EmozioneDao {
        return EmozioneDao(this)
    }

    /**
     * Restituisce un'istanza di ConsiglioDao per accedere alle operazioni del database per la tabella 'consigli'.
     *
     * @return Un'istanza di ConsiglioDao.
     */
    fun getConsiglioDao(): ConsiglioDao {
        return ConsiglioDao(this)
    }

    /**
     * Restituisce un'istanza di StatoEmozionaleDao per accedere alle operazioni del database per la tabella 'stato_emozionale'.
     *
     * @return Un'istanza di StatoEmozionaleDao.
     */
    fun getStatoEmozionaleDao(): StatoEmozionaleDao {
        return StatoEmozionaleDao(this)
    }
}
