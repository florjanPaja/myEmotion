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

class StatisticheActivity : AppCompatActivity() {

    private lateinit var testataLayout: LinearLayout
    private lateinit var filtroRadioGroup: RadioGroup
    private lateinit var settimanaRadioButton: RadioButton
    private lateinit var meseRadioButton: RadioButton
    private lateinit var annoRadioButton: RadioButton
    private lateinit var emozionePiuProvataTextView: TextView
    private lateinit var dbHelper: EmotionDatabaseHelper
    private lateinit var statoEmozionaleTable: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.statistiche)
        dbHelper = EmotionDatabaseHelper(this)

        // Inizializzazione dei componenti
        testataLayout = findViewById(R.id.testataLayout)
        filtroRadioGroup = findViewById(R.id.filtroRadioGroup)
        settimanaRadioButton = findViewById(R.id.settimanaRadioButton)
        meseRadioButton = findViewById(R.id.meseRadioButton)
        annoRadioButton = findViewById(R.id.annoRadioButton)
        emozionePiuProvataTextView = findViewById(R.id.emozionePiuProvataTextView)
        statoEmozionaleTable = findViewById(R.id.statoEmozionaleTable)

        emozionePiuProvataTextView.text =
            buildString {
                append(emozionePiuProvataTextView.text.toString())
                append(dbHelper.getStatoEmozionaleDao().getStatoMaggiore())
            }
        val listByWeekIn:List<EmozioneStatistica> = dbHelper.getStatoEmozionaleDao().getStatoEmozionale("SETTIMANA")
        updateTableView(listByWeekIn)
        // Listener per il RadioGroup
        filtroRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.settimanaRadioButton -> {
                    val listByWeek:List<EmozioneStatistica> = dbHelper.getStatoEmozionaleDao().getStatoEmozionale("SETTIMANA")
                    updateTableView(listByWeek)
                }

                R.id.meseRadioButton -> {
                    val listByMonth: List<EmozioneStatistica> = dbHelper.getStatoEmozionaleDao().getStatoEmozionale("MESE")
                    updateTableView(listByMonth)
                }

                R.id.annoRadioButton -> {
                    val listByYear: List<EmozioneStatistica> = dbHelper.getStatoEmozionaleDao().getStatoEmozionale("ANNO")
                    updateTableView(listByYear)
                }
            }
       }
    }

    // Funzione per aggiornare la TableView con i dati forniti
    private fun updateTableView(data: List<EmozioneStatistica>) {
        // Rimuove tutte le viste esistenti dalla TableView per svuotarla
        statoEmozionaleTable.removeViews(1, statoEmozionaleTable.childCount - 1)
var newdata = StatoEmozionaleList(data).convertListToPercentageMap();
        // Itera su ogni elemento nella mappa dei dati fornita
        for ((key, value) in newdata) {
            // Crea un nuovo LinearLayout da usare come riga nella TableView
            val row = LinearLayout(this)
            // Imposta i parametri di layout per la riga per avere larghezza corrispondente al genitore e altezza al contenuto
            row.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            // Imposta l'orientamento della riga a orizzontale
            row.orientation = LinearLayout.HORIZONTAL

            // Crea un TextView per l'emozione (chiave)
            val emotionTextView = TextView(this)
            // Imposta il testo del TextView alla chiave dell'emozione
            emotionTextView.text = key
            // Imposta i parametri di layout per il TextView per avere un peso di 1
            // Questo garantisce che occupi metà della larghezza della riga
            emotionTextView.layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )

            // Crea un TextView per il conteggio (valore)
            val countTextView = TextView(this)
            // Imposta il testo del TextView al valore dell'emozione convertito in stringa
            countTextView.text = value.toString()
            // Imposta i parametri di layout per il TextView per avere un peso di 1
            // Questo garantisce che occupi l'altra metà della larghezza della riga
            countTextView.layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )

            // Aggiunge il TextView dell'emozione alla riga
            row.addView(emotionTextView)
            // Aggiunge il TextView del conteggio alla riga
            row.addView(countTextView)
            // Aggiunge la riga alla TableView
            statoEmozionaleTable.addView(row)
        }
    }

}
