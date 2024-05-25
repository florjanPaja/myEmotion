package com.example.myemotion.db.database

import android.content.Context
import com.example.myemotion.db.dao.EmozioneDao
import com.example.myemotion.db.dao.ConsiglioDao
import com.example.myemotion.db.entity.Emozione
import com.example.myemotion.db.entity.Consiglio

class SetOnStart(dbHelper: EmotionDatabaseHelper) {
    private val dbHelper: EmotionDatabaseHelper = dbHelper
    fun initializeDatabase() {
        val emozioneDao = EmozioneDao(dbHelper)
        val consiglioDao = ConsiglioDao(dbHelper)

        val emozione1 =
            Emozione(nome = "Felicità", descrizione = "Sensazione di grande gioia e piacere")
        val emozione2 =
            Emozione(nome = "Tristezza", descrizione = "Sensazione di infelicità e abbattimento")
        val emozione3 =
            Emozione(nome = "Rabbia", descrizione = "Sensazione di irritazione e frustrazione")
        val emozione4 =
            Emozione(nome = "Paura", descrizione = "Sensazione di timore o preoccupazione")

        val idEmozione1 = emozioneDao.inserisciEmozione(emozione1)
        val idEmozione2 = emozioneDao.inserisciEmozione(emozione2)
        val idEmozione3 = emozioneDao.inserisciEmozione(emozione3)
        val idEmozione4 = emozioneDao.inserisciEmozione(emozione4)

        val consiglio1 = Consiglio(
            consiglio = "Sorridi di più",
            idEmozione = idEmozione1.toInt(),
            numDiVolteVisualizzata = 0
        )
        val consiglio2 = Consiglio(
            consiglio = "Parla con un amico",
            idEmozione = idEmozione2.toInt(),
            numDiVolteVisualizzata = 0
        )
        val consiglio3 = Consiglio(
            consiglio = "Fai un respiro profondo",
            idEmozione = idEmozione3.toInt(),
            numDiVolteVisualizzata = 0
        )
        val consiglio4 = Consiglio(
            consiglio = "Affronta le tue paure",
            idEmozione = idEmozione4.toInt(),
            numDiVolteVisualizzata = 0
        )

        consiglioDao.inserisciConsiglio(consiglio1)
        consiglioDao.inserisciConsiglio(consiglio2)
        consiglioDao.inserisciConsiglio(consiglio3)
        consiglioDao.inserisciConsiglio(consiglio4)
    }
}
