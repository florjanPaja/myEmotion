package com.example.myemotion

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myemotion.db.database.EmDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var emozioneSpinner: Spinner
    private lateinit var intensitaSeekBar: SeekBar
    private lateinit var intensitaTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val db = EmDatabase.getDatabase(this)
        // Configura i padding per i system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inizializza gli elementi UI
        emozioneSpinner = findViewById(R.id.emozioneSpinner)
        intensitaSeekBar = findViewById(R.id.intensitaSeekBar)
        intensitaTextView = findViewById(R.id.intensitaTextView)

        // Configura lo Spinner con un elenco di emozioni
        val emozioni = db.emozioneDao().getEmozioniList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, emozioni)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        emozioneSpinner.adapter = adapter

        // Configura il SeekBar per mostrare l'intensità selezionata
        intensitaSeekBar.max = 10
        intensitaSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                intensitaTextView.text = getString(R.string.intensita_valore, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Non serve implementare questo metodo
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Non serve implementare questo metodo
            }
        })

        // Imposta il valore iniziale del SeekBar
        intensitaTextView.text = getString(R.string.intensita_valore, intensitaSeekBar.progress)
    }
}
