package com.example.myemotion.utils

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import com.example.myemotion.R

/**
 * La classe AvvisiUtils fornisce metodi utilitari per mostrare avvisi e messaggi all'utente.
 * Attualmente, include un metodo per mostrare un dialogo con un messaggio e un pulsante di chiusura.
 */
object AvvisiUtils {

    /**
     * Mostra un dialogo con un messaggio all'utente.
     *
     * @param context Il contesto in cui il dialogo deve essere mostrato.
     * @param messaggio Il messaggio da visualizzare nel dialogo. Può essere nullo.
     */
    fun showMessage(context: Context, messaggio: String?) {
        // Crea una nuova istanza di Dialog utilizzando uno stile personalizzato.
        val dialog = Dialog(context, R.style.CustomDialogTheme)

        // Richiede di non mostrare il titolo del dialogo.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        // Imposta il layout del dialogo utilizzando un file di layout XML.
        dialog.setContentView(R.layout.popup)

        // Configura i parametri di layout per il dialogo.
        val layoutParams = dialog.window?.attributes
        layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT // Imposta la larghezza del dialogo a MATCH_PARENT.
        layoutParams?.height = ViewGroup.LayoutParams.WRAP_CONTENT // Imposta l'altezza del dialogo a WRAP_CONTENT.
        layoutParams?.gravity = Gravity.CENTER // Centra il dialogo sullo schermo.
        dialog.window?.attributes = layoutParams

        // Trova e aggiorna il TextView per il messaggio all'interno del dialogo.
        val messageTextView = dialog.findViewById<TextView>(R.id.messageTextView)
        messageTextView.text = messaggio

        // Trova il pulsante di chiusura e imposta un listener per chiudere il dialogo quando viene cliccato.
        val closeButton = dialog.findViewById<TextView>(R.id.closeButton)
        closeButton.setOnClickListener {
            dialog.dismiss() // Chiude il dialogo.
        }

        // Mostra il dialogo all'utente.
        dialog.show()
    }
}


