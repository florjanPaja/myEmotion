package com.example.myemotion.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myemotion.db.dao.ConsiglioDao
import com.example.myemotion.db.dao.EmozioneDao
import com.example.myemotion.db.dao.StatoEmozionaleDao
import com.example.myemotion.db.entity.Consiglio
import com.example.myemotion.db.entity.Emozione
import com.example.myemotion.db.entity.StatoEmozionale

@Database(entities = [Emozione::class, Consiglio::class, StatoEmozionale::class], version = 1)
abstract class EmDatabase : RoomDatabase() {
    abstract fun emozioneDao(): EmozioneDao
    abstract fun consiglioDao(): ConsiglioDao
    abstract fun statoEmozionaleDao(): StatoEmozionaleDao

    companion object {
        @Volatile
        private var instance: EmDatabase? = null

        fun getDatabase(context: Context): EmDatabase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    EmDatabase::class.java,
                    "database"
                ).build()
                instance = newInstance
                newInstance
            }
        }
    }


    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            populateDatabase(instance!!)
        }

        fun populateDatabase(db: EmDatabase) {
            val emozioneDao = db.emozioneDao()
            val consiglioDao = db.consiglioDao()
            val statoEmozionaleDao = db.statoEmozionaleDao()

            // Esempio di dati iniziali
            val emozione1 = Emozione(nome = "Felicità", descrizione = "Sensazione di grande gioia e piacere")
            val emozione2 = Emozione(nome = "Tristezza", descrizione = "Sensazione di infelicità e abbattimento")
            val emozione3 = Emozione(nome = "Rabbia", descrizione = "Sensazione di irritazione e frustrazione")
            val emozione4 = Emozione(nome = "Paura", descrizione = "Sensazione di timore o preoccupazione")

            val idEmozione1 = emozioneDao.inserisciEmozione(emozione1)
            val idEmozione2 = emozioneDao.inserisciEmozione(emozione2)
            val idEmozione3 = emozioneDao.inserisciEmozione(emozione3)
            val idEmozione4 = emozioneDao.inserisciEmozione(emozione4)

            val consiglio1 = Consiglio(consiglio = "Sorridi di più", idEmozione = 1, numDiVolteVisualizzata = 0)
            val consiglio2 = Consiglio(consiglio = "Parla con un amico", idEmozione = 2, numDiVolteVisualizzata = 0)
            val consiglio3 = Consiglio(consiglio = "Fai un respiro profondo", idEmozione = 3, numDiVolteVisualizzata = 0)
            val consiglio4 = Consiglio(consiglio = "Affronta le tue paure", idEmozione = 4, numDiVolteVisualizzata = 0)

            consiglioDao.inserisciConsiglio(consiglio1)
            consiglioDao.inserisciConsiglio(consiglio2)
            consiglioDao.inserisciConsiglio(consiglio3)
            consiglioDao.inserisciConsiglio(consiglio4)


        }

        }
    }



