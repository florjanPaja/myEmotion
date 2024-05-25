package com.example.myemotion;
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myemotion.R
import com.example.myemotion.db.database.EmotionDatabaseHelper

class StatisticheActivity : AppCompatActivity() {

    private lateinit var testataLayout: LinearLayout
    private lateinit var tutteLeEmozioniLayout: LinearLayout
    private lateinit var filtroRadioGroup: RadioGroup
    private lateinit var settimanaRadioButton: RadioButton
    private lateinit var meseRadioButton: RadioButton
    private lateinit var annoRadioButton: RadioButton
    private lateinit var emozionePiuProvataTextView: TextView
    private lateinit var emozionePiuProvataPercentualeTextView: TextView
    private lateinit var dbHelper: EmotionDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.statistiche)
        dbHelper = EmotionDatabaseHelper(this)
        // Inizializzazione dei componenti
        testataLayout = findViewById(R.id.testataLayout)
        tutteLeEmozioniLayout = findViewById(R.id.tutteLeEmozioniLayout)
        filtroRadioGroup = findViewById(R.id.filtroRadioGroup)
        settimanaRadioButton = findViewById(R.id.settimanaRadioButton)
        meseRadioButton = findViewById(R.id.meseRadioButton)
        annoRadioButton = findViewById(R.id.annoRadioButton)
        emozionePiuProvataTextView = findViewById(R.id.emozionePiuProvataTextView)
        emozionePiuProvataPercentualeTextView = findViewById(R.id.emozionePiuProvataPercentualeTextView)

        // Codice aggiuntivo per gestire il RadioGroup
        filtroRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            // Logica per gestire la selezione del RadioGroup
            when (checkedId) {
                R.id.settimanaRadioButton -> {
                    // Logica per la selezione della settimana
                }
                R.id.meseRadioButton -> {
                    // Logica per la selezione del mese
                }
                R.id.annoRadioButton -> {
                    // Logica per la selezione dell'anno
                }
            }
        }
    }
}
