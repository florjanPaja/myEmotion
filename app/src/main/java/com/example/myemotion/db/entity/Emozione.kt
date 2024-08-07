package com.example.myemotion.db.entity

/**
 * Rappresenta un'emozione nel sistema.
 *
 * @property id L'ID univoco dell'emozione, utilizzato come chiave primaria nel database. Di default è 0 e viene automaticamente gestito dal database.
 * @property nome Il nome dell'emozione, come "Felicità", "Tristezza", ecc.
 * @property descrizione Una breve descrizione dell'emozione, che fornisce ulteriori dettagli o contestualizzazione.
 */
data class Emozione(
    val id: Int = 0, // ID dell'emozione, con valore predefinito 0
    val nome: String, // Nome dell'emozione (es. "Felicità")
    val descrizione: String, // Descrizione dell'emozione (es. "Sensazione di grande gioia e piacere")
)
