package com.example.myemotion

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.example.myemotion.db.database.EmotionDatabaseHelper
import com.example.myemotion.db.entity.StatoEmozionale
import com.example.myemotion.utils.AvvisiUtils
import java.util.Date

class MainMetodi(dbHelper: EmotionDatabaseHelper) {
    private val dbHelper: EmotionDatabaseHelper = dbHelper
    fun saveStatoEmozionale(selectedNome: String, selectedId: Int, intensita: Int, nota: String) {
        val data = Date()

        val statoEmozionale = StatoEmozionale(
            idEmozione = selectedId,
            nomeEmozione = selectedNome,
            intensita = intensita,
            nota = nota,
            data = data
        )

        dbHelper.getStatoEmozionaleDao().inserisciStatoEmozionale(statoEmozionale)
    }
    fun showConsiglio(context: Context, idEmozione: Int) {
        val consiglio = dbHelper.getConsiglioDao().showConsiglio(idEmozione)

        AvvisiUtils.showMessage(context, consiglio)
    }


}