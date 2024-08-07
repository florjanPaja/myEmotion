package com.example.myemotion.db.entity

import java.util.Date

/**
 * Rappresenta lo stato emozionale di un utente in un momento specifico.
 *
 * @property id L'ID univoco dello stato emozionale, utilizzato come chiave primaria nel database. Di default è 0 e viene automaticamente gestito dal database.
 * @property idEmozione L'ID dell'emozione associata a questo stato emozionale.
 * @property nomeEmozione Il nome dell'emozione associata, come "Felicità", "Tristezza", ecc.
 * @property intensita Il livello di intensità dell'emozione, espresso come intero (es. da 1 a 10).
 * @property nota Una nota opzionale aggiunta dall'utente per descrivere ulteriormente il suo stato emozionale.
 * @property data La data e l'ora in cui l'emozione è stata registrata.
 */
data class StatoEmozionale(
    val id: Int = 0, // ID dello stato emozionale, con valore predefinito 0
    val idEmozione: Int, // ID dell'emozione associata
    val nomeEmozione: String, // Nome dell'emozione (es. "Felicità")
    val intensita: Int, // Intensità dell'emozione (es. da 1 a 10)
    val nota: String, // Nota opzionale per descrivere lo stato emozionale
    val data: Date // Data e ora in cui l'emozione è stata registrata
)
