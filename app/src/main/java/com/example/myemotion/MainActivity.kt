package com.example.myemotion

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myemotion.db.database.EmotionDatabaseHelper
import com.example.myemotion.db.database.SetOnStart
import com.example.myemotion.utils.AvvisiUtils

/**
 * MainActivity è il punto di ingresso principale dell'applicazione.
 * Permette agli utenti di selezionare un'emozione, regolare la sua intensità, aggiungere note e salvare lo stato emotivo.
 * Offre anche un'opzione per visualizzare le statistiche.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var emozioneSpinner: Spinner
    private lateinit var intensitaSeekBar: SeekBar
    private lateinit var intensitaTextView: TextView
    private lateinit var noteEditText: EditText
    private lateinit var salvaButton: Button
    private lateinit var statistichebutton: Button
    private lateinit var dbHelper: EmotionDatabaseHelper
    private lateinit var emozioniMap: Map<String, Int>

    /**
     * Viene chiamato quando l'attività viene creata per la prima volta.
     * Inizializza gli elementi dell'interfaccia utente, configura lo Spinner e il SeekBar, e imposta i listener per i pulsanti.
     *
     * @param savedInstanceState Il bundle dello stato salvato se l'attività viene ricreata.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        dbHelper = EmotionDatabaseHelper(this)
        val setOnStart = SetOnStart(dbHelper)
        setOnStart.initializeDatabase()

        // Configura il padding per le system bars per garantire che il contenuto non sia nascosto.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inizializza gli elementi dell'interfaccia utente
        emozioneSpinner = findViewById(R.id.emozioneSpinner)
        intensitaSeekBar = findViewById(R.id.intensitaSeekBar)
        intensitaTextView = findViewById(R.id.intensitaTextView)
        noteEditText = findViewById(R.id.noteEditText)
        salvaButton = findViewById(R.id.salvaButton)
        statistichebutton = findViewById(R.id.vediStatisticheButton)

        // Configura lo Spinner con un elenco di emozioni
        emozioniMap = dbHelper.getEmozioneDao().getNomiEmozioni()
        val nomiEmozioni = mutableListOf<String>()
        nomiEmozioni.add("") // Aggiungi un elemento vuoto all'inizio della lista
        nomiEmozioni.addAll(emozioniMap.keys)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nomiEmozioni)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        emozioneSpinner.adapter = adapter

        // Configura il SeekBar per visualizzare l'intensità selezionata
        intensitaSeekBar.min = 1
        intensitaSeekBar.max = 10
        intensitaSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                intensitaTextView.text = getString(R.string.intensita_valore, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // Imposta il valore iniziale del SeekBar
        intensitaTextView.text = getString(R.string.intensita_valore, intensitaSeekBar.progress)

        // Imposta il listener per il pulsante Salva
        salvaButton.setOnClickListener {
            if (emozioneSpinner.selectedItem != "") {
                Metodi(dbHelper).saveStatoEmozionale(
                    emozioneSpinner.selectedItem as String,
                    emozioniMap[emozioneSpinner.selectedItem as String] ?: 0,
                    intensitaSeekBar.progress,
                    noteEditText.text.toString()
                )
                Metodi(dbHelper).showConsiglio(
                    this,
                    emozioniMap[emozioneSpinner.selectedItem as String] ?: 0
                )
                // Reimposta la selezione dello Spinner
                emozioneSpinner.setSelection(0)
                // Reimposta il progresso del SeekBar
                intensitaSeekBar.progress = 1
                // Cancella il testo nell'EditText
                noteEditText.text = null
            } else {
                AvvisiUtils.showMessage(this, "NESSUNA EMOZIONE INSERITA")
            }
        }

        // Imposta il listener per il pulsante Statistiche
        statistichebutton.setOnClickListener {
            val intent = Intent(this, StatisticheActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Viene chiamato quando l'attività sta per essere distrutta.
     * Chiude il database helper per liberare le risorse.
     */
    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
