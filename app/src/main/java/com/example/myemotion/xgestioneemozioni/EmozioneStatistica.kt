package com.example.myemotion.xgestioneemozioni

/**
 * La classe EmozioneStatistica rappresenta le statistiche di un'emozione specifica.
 * Contiene informazioni sul nome dell'emozione, la media dell'intensità e il conteggio delle occorrenze.
 *
 * @property nome Il nome dell'emozione.
 * @property mediaIntensita La media dell'intensità dell'emozione.
 * @property conteggio Il numero di volte che l'emozione è stata registrata.
 */
data class EmozioneStatistica(
    val nome: String, // Il nome dell'emozione, ad esempio "Gioia", "Tristezza", ecc.
    val mediaIntensita: Double, // La media dell'intensità con cui l'emozione è stata sperimentata.
    val conteggio: Int, // Il conteggio totale delle volte in cui l'emozione è stata registrata.
)
