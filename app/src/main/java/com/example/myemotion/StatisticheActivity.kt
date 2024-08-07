package com.example.myemotion

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myemotion.xgestioneemozioni.StatoEmozionaleList
import com.example.myemotion.db.database.EmotionDatabaseHelper
import com.example.myemotion.xgestioneemozioni.EmozioneStatistica

/**
 * StatisticheActivity gestisce la visualizzazione delle statistiche relative alle emozioni.
 * Permette all'utente di filtrare le statistiche per settimana, mese o anno e mostra i risultati in una tabella.
 */
class StatisticheActivity : AppCompatActivity() {

    private lateinit var testataLayout: LinearLayout
    private lateinit var filtroRadioGroup: RadioGroup
    private lateinit var settimanaRadioButton: RadioButton
    private lateinit var meseRadioButton: RadioButton
    private lateinit var annoRadioButton: RadioButton
    private lateinit var emozionePiuProvataTextView: TextView
    private lateinit var dbHelper: EmotionDatabaseHelper
    private lateinit var statoEmozionaleTable: LinearLayout

    /**
     * Viene chiamato quando l'attività viene creata per la prima volta.
     * Inizializza i componenti dell'interfaccia utente e imposta i listener per i controlli.
     *
     * @param savedInstanceState Il bundle dello stato salvato se l'attività viene ricreata.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.statistiche)
        dbHelper = EmotionDatabaseHelper(this)

        // Inizializzazione dei componenti dell'interfaccia utente
        testataLayout = findViewById(R.id.testataLayout)
        filtroRadioGroup = findViewById(R.id.filtroRadioGroup)
        settimanaRadioButton = findViewById(R.id.settimanaRadioButton)
        meseRadioButton = findViewById(R.id.meseRadioButton)
        annoRadioButton = findViewById(R.id.annoRadioButton)
        emozionePiuProvataTextView = findViewById(R.id.emozionePiuProvataTextView)
        statoEmozionaleTable = findViewById(R.id.statoEmozionaleTable)

        // Imposta il testo della TextView con l'emozione più provata
        emozionePiuProvataTextView.text =
            buildString {
                append(emozionePiuProvataTextView.text.toString())
                append(dbHelper.getStatoEmozionaleDao().getStatoMaggiore())
            }

        // Recupera e visualizza le statistiche settimanali per default
        val listByWeek: List<EmozioneStatistica> = dbHelper.getStatoEmozionaleDao().getStatoEmozionale("SETTIMANA")
        updateTableView(listByWeek)

        // Imposta il listener per il RadioGroup per cambiare il filtro delle statistiche
        filtroRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.settimanaRadioButton -> {
                    // Recupera e visualizza le statistiche settimanali
                    val listByWeek: List<EmozioneStatistica> = dbHelper.getStatoEmozionaleDao().getStatoEmozionale("SETTIMANA")
                    updateTableView(listByWeek)
                }

                R.id.meseRadioButton -> {
                    // Recupera e visualizza le statistiche mensili
                    val listByMonth: List<EmozioneStatistica> = dbHelper.getStatoEmozionaleDao().getStatoEmozionale("MESE")
                    updateTableView(listByMonth)
                }

                R.id.annoRadioButton -> {
                    // Recupera e visualizza le statistiche annuali
                    val listByYear: List<EmozioneStatistica> = dbHelper.getStatoEmozionaleDao().getStatoEmozionale("ANNO")
                    updateTableView(listByYear)
                }
            }
        }
    }

    /**
     * Aggiorna la TableView con i dati forniti.
     * Pulisce la TableView esistente e aggiunge nuove righe basate sui dati.
     *
     * @param data La lista di dati delle statistiche da visualizzare nella tabella.
     */
    private fun updateTableView(data: List<EmozioneStatistica>) {
        // Rimuove tutte le viste esistenti dalla TableView per svuotarla
        statoEmozionaleTable.removeViews(1, statoEmozionaleTable.childCount - 1)

        // Converte la lista di statistiche in una mappa di percentuali
        val newData = StatoEmozionaleList(data).convertListToPercentageMap()

        // Itera su ogni elemento nella mappa dei dati fornita
        for ((key, value) in newData) {
            // Crea un nuovo LinearLayout per una riga della tabella
            val row = LinearLayout(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                orientation = LinearLayout.HORIZONTAL
            }

            // Crea un TextView per l'emozione (chiave)
            val emotionTextView = TextView(this).apply {
                text = key
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
            }

            // Crea un TextView per il conteggio (valore)
            val countTextView = TextView(this).apply {
                text = value.toString()
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
            }

            // Aggiunge i TextView alla riga
            row.addView(emotionTextView)
            row.addView(countTextView)

            // Aggiunge la riga alla TableView
            statoEmozionaleTable.addView(row)
        }
    }
}
