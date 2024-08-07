package com.example.myemotion

import android.content.Context
import com.example.myemotion.db.database.EmotionDatabaseHelper
import com.example.myemotion.db.entity.StatoEmozionale
import com.example.myemotion.utils.AvvisiUtils
import java.util.Date

/**
 * La classe Metodi fornisce metodi utilitari per gestire e salvare lo stato emotivo dell'utente.
 * Utilizza un'istanza di EmotionDatabaseHelper per interagire con il database.
 *
 * @property dbHelper L'oggetto EmotionDatabaseHelper utilizzato per accedere al database.
 */
class Metodi(private val dbHelper: EmotionDatabaseHelper) {

    /**
     * Salva lo stato emotivo dell'utente nel database.
     *
     * @param selectedNome Il nome dell'emozione selezionata.
     * @param selectedId L'ID dell'emozione selezionata.
     * @param intensita Il livello di intensità dell'emozione (da 1 a 10).
     * @param nota Una nota aggiuntiva riguardante l'emozione.
     */
    fun saveStatoEmozionale(selectedNome: String, selectedId: Int, intensita: Int, nota: String) {
        // Crea un'istanza di StatoEmozionale con i dati forniti
        val statoEmozionale = StatoEmozionale(
            idEmozione = selectedId,
            nomeEmozione = selectedNome,
            intensita = intensita,
            nota = nota,
            data = Date() // Imposta la data corrente
        )

        // Inserisce lo stato emotivo nel database utilizzando il DAO appropriato
        dbHelper.getStatoEmozionaleDao().inserisciStatoEmozionale(statoEmozionale)
    }

    /**
     * Mostra un consiglio relativo all'emozione selezionata.
     *
     * @param context Il contesto dell'applicazione, utilizzato per mostrare il messaggio.
     * @param idEmozione L'ID dell'emozione per cui si desidera ottenere un consiglio.
     */
    fun showConsiglio(context: Context, idEmozione: Int) {
        // Recupera il consiglio dal DAO basato sull'ID dell'emozione
        val consiglio = dbHelper.getConsiglioDao().showConsiglio(idEmozione)

        // Mostra il consiglio all'utente utilizzando AvvisiUtils
        AvvisiUtils.showMessage(context, consiglio)
    }
}
