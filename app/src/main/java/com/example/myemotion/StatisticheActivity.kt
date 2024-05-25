package com.example.myemotion
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myemotion.db.database.EmotionDatabaseHelper

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

        // Listener per il RadioGroup
        filtroRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.settimanaRadioButton -> {
                    val listByWeek: Map<String, Int> = dbHelper.getStatoEmozionaleDao().getStatoWEEK()
                    updateTableView(listByWeek)
                }

                R.id.meseRadioButton -> {
                    val listByMonth: Map<String, Int> = dbHelper.getStatoEmozionaleDao().getStatoMONTH()
                    updateTableView(listByMonth)
                }

                R.id.annoRadioButton -> {
                    val listByYear: Map<String, Int> = dbHelper.getStatoEmozionaleDao().getStatoYEAR()
                    updateTableView(listByYear)
                }
            }
        }
    }

    private fun updateTableView(data: Map<String, Int>) {
        statoEmozionaleTable.removeAllViews()
        for ((key, value) in data) {
            val row = LinearLayout(this)
            row.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            row.orientation = LinearLayout.HORIZONTAL

            val emotionTextView = TextView(this)
            emotionTextView.text = key
            emotionTextView.layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )

            val countTextView = TextView(this)
            countTextView.text = value.toString()
            countTextView.layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )

            row.addView(emotionTextView)
            row.addView(countTextView)
            statoEmozionaleTable.addView(row)
        }
    }
}
